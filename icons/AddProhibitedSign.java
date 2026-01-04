import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddProhibitedSign {
    
    public static void addProhibitedSign(String inputPath, String outputPath) throws IOException {
        // Читаем исходное изображение
        File inputFile = new File(inputPath);
        BufferedImage originalImage = ImageIO.read(inputFile);
        
        if (originalImage == null) {
            throw new IOException("Не удалось загрузить изображение: " + inputPath);
        }
        
        // Создаем новое изображение с прозрачностью
        BufferedImage resultImage = new BufferedImage(
            originalImage.getWidth(),
            originalImage.getHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        
        // Копируем оригинальное изображение
        Graphics2D g2d = resultImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        
        // Настройки для рисования
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Размеры
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int size = (int) (Math.min(width, height) * 0.6); // 60% от минимального размера
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        // Центр круга
        int radius = size / 2;
        int centerX = x + radius;
        int centerY = y + radius;
        
        Color redSolid = new Color(204, 0, 0, 255); // Сплошной красный для контура
        // Контур круга
        g2d.setColor(redSolid);
        g2d.setStroke(new BasicStroke(Math.max(6.0f, size / 40))); // Адаптивная толщина линии
        g2d.drawOval(x, y, size, size);
        // Диагональная линия (перечеркивание)
        float lineThickness = Math.max(6.0f, size / 25.0f); // Адаптивная толщина линии
        g2d.setStroke(new BasicStroke(lineThickness));
        // Угол 135 градусов (диагональ)
        double angle = Math.toRadians(135); // 135 градусов в радианах
        double cos135 = Math.cos(angle);
        double sin135 = Math.sin(angle);
        // Точка A: верхний левый сегмент окружности
        int x1 = centerX - (int)(radius * cos135);
        int y1 = centerY - (int)(radius * sin135);
        // Точка B: нижний правый сегмент окружности  
        int x2 = centerX + (int)(radius * cos135);
        int y2 = centerY + (int)(radius * sin135);
        // Рисуем линию внутри круга
        g2d.drawLine(x1, y1, x2, y2);

        // // ===== Текст "CLOSED" зелеными буквами =====
        // int fontSize = Math.max(12, width / 8); // Минимум 12px, примерно 1/8 от ширины
        // Font font = new Font("Arial", Font.BOLD, fontSize);
        // g2d.setFont(font);
        // // Получаем размеры текста
        // FontMetrics fm = g2d.getFontMetrics();
        // String text = "CLOSED";
        // int textWidth = fm.stringWidth(text);
        // int textHeight = fm.getHeight();
        // // Центрируем текст по горизонтали
        // int textX = (width - textWidth) / 2;
        // int textY = centerY + textHeight; // Центрируем по вертикали в нижней части
        // // Добавляем тень для лучшей читаемости (опционально)
        // g2d.setColor(new Color(0, 0, 0, 100)); // Полупрозрачный черный для тени
        // g2d.drawString(text, textX + 1, textY + 1); // Смещенная тень
        // // 6. Рисуем основной текст
        // g2d.setColor(new Color(0, 0, 0, 255)); // Черный
        // g2d.drawString(text, textX, textY); // Рисуем текст

        g2d.dispose();
        
        // Сохраняем результат
        String format = getFileExtension(outputPath);
        File outputFile = new File(outputPath);
        ImageIO.write(resultImage, format, outputFile);
        
        System.out.println("Изображение сохранено: " + outputFile.getAbsolutePath());
    }
    
    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "png"; // По умолчанию
    }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Использование: java AddProhibitedSign <входной_файл> <выходной_файл>");
            System.out.println("Пример: java AddProhibitedSign input.jpg output.png");
            System.exit(1);
        }
        
        try {
            addProhibitedSign(args[0], args[1]);
            System.out.println("Готово!");
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}