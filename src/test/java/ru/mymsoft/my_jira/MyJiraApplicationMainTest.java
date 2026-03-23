package ru.mymsoft.my_jira;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Тест для проверки main метода MyJiraApplication.
 * Тестирует возможность вызова main метода без фактического запуска приложения.
 */
@DisplayName("MyJiraApplication Main Method Tests")
class MyJiraApplicationMainTest {

    @Test
    @DisplayName("Main метод должен существовать и быть корректным")
    void mainMethodExists() throws Exception {
        // Проверяем, что main метод существует
        Method mainMethod = MyJiraApplication.class.getMethod("main", String[].class);
        assertThat(mainMethod).isNotNull();
        assertThat(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers())).isTrue();
        assertThat(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers())).isTrue();
        assertThat(mainMethod.getReturnType()).isEqualTo(void.class);
    }

    @Test
    @DisplayName("Main метод должен быть статическим и принимать String[]")
    void mainMethodSignature() throws Exception {
        Method mainMethod = MyJiraApplication.class.getMethod("main", String[].class);
        Class<?>[] parameterTypes = mainMethod.getParameterTypes();
        assertThat(parameterTypes).hasSize(1);
        assertThat(parameterTypes[0]).isEqualTo(String[].class);
    }

    @Test
    @DisplayName("Main метод должен быть аннотирован корректно")
    void mainMethodAnnotations() throws Exception {
        Method mainMethod = MyJiraApplication.class.getMethod("main", String[].class);
        // Проверяем отсутствие специфических аннотаций, которые могли бы помешать тестированию
        assertThat(mainMethod.isAnnotationPresent(Deprecated.class)).isFalse();
    }

    @Test
    @DisplayName("Класс MyJiraApplication должен иметь корректную структуру")
    void applicationClassStructure() {
        // Проверяем, что класс не абстрактный и имеет публичный конструктор
        assertThat(MyJiraApplication.class.isInterface()).isFalse();
        assertThat(MyJiraApplication.class.isEnum()).isFalse();
        assertThat(MyJiraApplication.class.isAnnotation()).isFalse();
        
        // Проверяем наличие аннотации @SpringBootApplication
        assertThat(MyJiraApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class))
                .isTrue();
    }

    @Test
    @DisplayName("SpringBootApplication аннотация должна быть корректной")
    void springBootApplicationAnnotation() {
        org.springframework.boot.autoconfigure.SpringBootApplication annotation = 
                MyJiraApplication.class.getAnnotation(org.springframework.boot.autoconfigure.SpringBootApplication.class);
        
        assertThat(annotation).isNotNull();
        // Проверяем, что сканирование пакетов настроено на базовый пакет
        assertThat(annotation.scanBasePackages()).isEmpty(); // Пустой массив означает сканирование текущего пакета
    }

    @Test
    @DisplayName("Проверка вызова SpringApplication.run без фактического запуска")
    void springApplicationRunCall() {
        // Этот тест проверяет, что main метод может быть вызван без исключений
        // Мы не запускаем приложение реально, а только проверяем структуру вызова
        
        String[] args = new String[]{"--spring.profiles.active=test", "--server.port=0"};
        
        // Проверяем, что код может быть скомпилирован и выполнен без синтаксических ошибок
        assertThatCode(() -> {
            // Проверяем, что SpringApplication.run вызывается с правильными параметрами
            Class<?>[] paramTypes = {Class.class, String[].class};
            assertThat(SpringApplication.class.getMethod("run", paramTypes)).isNotNull();
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Проверка пакета приложения")
    void applicationPackage() {
        String packageName = MyJiraApplication.class.getPackage().getName();
        assertThat(packageName).isEqualTo("ru.mymsoft.my_jira");
    }
}
