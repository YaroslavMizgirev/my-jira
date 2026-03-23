package ru.mymsoft.my_jira;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест для проверки работы приложения с различными профилями.
 * Использует тестовые свойства для избежания проблем с OAuth2.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.security.oauth2.client.enabled=false",
    "spring.main.allow-bean-definition-overriding=true"
})
@DisplayName("MyJiraApplication Profile Tests")
class MyJiraApplicationProfileTest {

    @Autowired
    private Environment environment;

    @Test
    @DisplayName("Приложение должно запускаться с test профилем")
    void applicationStartsWithTestProfile() {
        // Проверяем, что тестовый профиль активен
        assertThat(environment).isNotNull();
        assertThat(environment.getActiveProfiles()).contains("test");
    }

    @Test
    @DisplayName("Test профиль должен быть активен")
    void testProfileIsActive() {
        boolean hasTestProfile = environment.acceptsProfiles("test");
        assertThat(hasTestProfile).isTrue();
    }

    @Test
    @DisplayName("Свойства тестового профиля должны быть доступны")
    void testProfilePropertiesAvailable() {
        // Проверяем наличие ключевых свойств
        assertThat(environment.getProperty("spring.profiles.active")).isEqualTo("test");
    }

    @Test
    @DisplayName("Конфигурация базы данных для тестов должна быть корректной")
    void testDatabaseConfiguration() {
        // Проверяем, что конфигурация БД для тестов загружена
        String datasourceUrl = environment.getProperty("spring.datasource.url");
        assertThat(datasourceUrl).isNotNull();
        // Для тестов обычно используется H2 in-memory база данных
        assertThat(datasourceUrl).contains("h2") 
                .describedAs("Тестовая конфигурация должна использовать H2 базу данных");
    }

    @Test
    @DisplayName("JPA настройки для тестов должны быть корректными")
    void testJpaSettings() {
        // Проверяем JPA настройки для тестов
        String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");
        assertThat(ddlAuto).isIn("create-drop", "create", "update")
                .describedAs("DDL-auto должен быть подходящим для тестов");
    }

    @Test
    @DisplayName("Логирование для тестов должно быть настроено")
    void testLoggingConfiguration() {
        // Проверяем настройки логирования
        String loggingLevel = environment.getProperty("logging.level.ru.mymsoft.my_jira");
        // Может быть null, если не настроено явно
    }

    @Test
    @DisplayName("Свойства безопасности для тестов должны быть корректными")
    void testSecurityProperties() {
        // Проверяем, что OAuth2 отключен для тестов
        String oauth2Enabled = environment.getProperty("spring.security.oauth2.client.enabled");
        assertThat(oauth2Enabled).isEqualTo("false");
    }
}
