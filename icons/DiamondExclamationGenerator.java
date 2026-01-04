import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class DiamondExclamationGenerator {
    
    public static BufferedImage createDiamondImage(int width, int height, Color color) {
        // Создаем изображение
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Настройки качества
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Прозрачный фон
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);
        g2d.setComposite(AlphaComposite.SrcOver);
        
        // Размеры ромба
        int diamondSize = Math.min(width, height) * 3 / 4;

        // Создаем ромб через повернутый квадрат со скругленными углами
        g2d.setColor(color);
        
        // Сохраняем текущую трансформацию
        AffineTransform oldTransform = g2d.getTransform();
        
        // Поворачиваем на 45 градусов вокруг центра
        g2d.rotate(Math.PI / 4, width / 2, height / 2);
        
        // Рисуем скругленный квадрат (после поворота будет ромбом)
        int rotatedSize = (int)(diamondSize / Math.sqrt(2));
        int rotatedX = (width - rotatedSize) / 2;
        int rotatedY = (height - rotatedSize) / 2;
        int cornerRadius = rotatedSize / 4; // Радиус скругления
        
        RoundRectangle2D roundedSquare = new RoundRectangle2D.Double(
            rotatedX, rotatedY, rotatedSize, rotatedSize, cornerRadius, cornerRadius);
        
        g2d.fill(roundedSquare);
        
        // Восстанавливаем трансформацию
        g2d.setTransform(oldTransform);
        
        // Рисуем восклицательный знак
        drawExclamationMark(g2d, width, height, diamondSize);
        
        g2d.dispose();
        return image;
    }

    private static void drawExclamationMark(Graphics2D g2d, int width, int height, int diamondSize) {
        // ===== Текст "!" белым цветом =====
        int fontSize = Math.max(18, diamondSize / 2); // Минимум 18px, примерно 1/2 от размера ромба
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);
        String text = "!";

        // Получаем размеры текста для точного центрирования
        int textWidth = g2d.getFontMetrics().stringWidth(text);
        int textHeight = g2d.getFontMetrics().getAscent();
        // Центрируем текст - теперь координаты указывают на левый нижний угол
        int textX = (width - textWidth) / 2;
        int textY = (height + textHeight) / 2 - g2d.getFontMetrics().getDescent();
        // Добавляем тень для лучшей читаемости (опционально)
        g2d.setColor(new Color(0, 0, 0, 100)); // Полупрозрачный черный для тени
        g2d.drawString(text, textX + 1, textY + 1); // Смещенная тень
        // Рисуем основной текст
        g2d.setColor(new Color(255, 255, 255, 255)); // Белый
        g2d.drawString(text, textX, textY); // Рисуем текст
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Генератор изображения с ромбом и восклицательным знаком ===\n");
        
        try {
            // Ввод размеров
            System.out.print("Введите ширину изображения (пикселей): ");
            int width = scanner.nextInt();
            
            System.out.print("Введите высоту изображения (пикселей): ");
            int height = scanner.nextInt();
            
            // Ввод цвета
            System.out.println("\nВыберите цвет ромба:");
            System.out.println("1. Красный");
            System.out.println("2. Синий");
            System.out.println("3. Зеленый");
            System.out.println("4. Желтый");
            System.out.println("5. Фиолетовый");
            System.out.println("6. Оранжевый");
            System.out.println("7. Ввести RGB вручную");
            
            System.out.print("Ваш выбор (1-7): ");
            int colorChoice = scanner.nextInt();
            
            Color diamondColor;
            
            switch (colorChoice) {
                case 1:
                    diamondColor = Color.RED;
                    break;
                case 2:
                    diamondColor = Color.BLUE;
                    break;
                case 3:
                    diamondColor = Color.GREEN;
                    break;
                case 4:
                    diamondColor = Color.YELLOW;
                    break;
                case 5:
                    diamondColor = new Color(128, 0, 128); // Фиолетовый
                    break;
                case 6:
                    diamondColor = Color.ORANGE;
                    break;
                case 7:
                    System.out.print("Введите R (0-255): ");
                    int r = scanner.nextInt();
                    System.out.print("Введите G (0-255): ");
                    int g = scanner.nextInt();
                    System.out.print("Введите B (0-255): ");
                    int b = scanner.nextInt();
                    diamondColor = new Color(r, g, b);
                    break;
                default:
                    System.out.println("Неверный выбор, используем красный по умолчанию");
                    diamondColor = Color.RED;
            }
            
            // Ввод имени файла
            System.out.print("\nВведите имя выходного файла (без расширения): ");
            String fileName = scanner.next();
            
            // Создание изображения
            System.out.println("\nСоздаю изображение...");
            BufferedImage image = createDiamondImage(width, height, diamondColor);
            
            // Сохранение
            String outputPath = fileName + ".png";
            File outputFile = new File(outputPath);
            ImageIO.write(image, "png", outputFile);
            
            System.out.println("✓ Изображение сохранено: " + outputFile.getAbsolutePath());
            System.out.println("✓ Размер: " + width + "x" + height);
            System.out.println("✓ Цвет ромба: RGB(" + diamondColor.getRed() + ", " + 
                             diamondColor.getGreen() + ", " + diamondColor.getBlue() + ")");
            
        } catch (Exception e) {
            System.err.println("✗ Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}