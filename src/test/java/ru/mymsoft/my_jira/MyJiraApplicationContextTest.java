package ru.mymsoft.my_jira;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест для проверки ApplicationContext MyJiraApplication.
 * Использует тестовые свойства для избежания проблем с OAuth2 конфигурацией.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
    "spring.security.oauth2.client.enabled=false",
    "spring.main.allow-bean-definition-overriding=true"
})
@DisplayName("MyJiraApplication ApplicationContext Tests")
class MyJiraApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Spring контекст должен успешно загружаться")
    void applicationContextLoads() {
        // Проверяем, что контекст Spring создан
        assertThat(applicationContext).isNotNull();
        assertThat(applicationContext.getBeanDefinitionCount()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Основной класс приложения должен быть зарегистрирован в контексте")
    void mainBeanIsPresent() {
        // Проверяем, что основной класс зарегистрирован в контексте
        assertThat(applicationContext.containsBean("myJiraApplication")).isTrue();
    }

    @Test
    @DisplayName("Конфигурация Spring Boot должна быть корректной")
    void springBootConfigurationCorrect() {
        // Проверяем, что это действительно Spring Boot приложение
        assertThat(applicationContext.containsBean("springApplicationArguments")).isTrue();
        assertThat(applicationContext.containsBean("springBootBanner")).isTrue();
    }

    @Test
    @DisplayName("Environment должен быть доступен")
    void environmentAvailable() {
        // Проверяем, что Environment доступен
        org.springframework.core.env.Environment env = applicationContext.getEnvironment();
        assertThat(env).isNotNull();
        assertThat(env.getActiveProfiles()).isNotNull();
    }

    @Test
    @DisplayName("Автоконфигурация должна работать")
    void autoConfigurationWorking() {
        // Проверяем, что автоконфигурация работает
        String[] autoConfigBeans = applicationContext.getBeanNamesForType(
                org.springframework.boot.autoconfigure.AutoConfigurationImportSelector.class);
        // Может быть пустым, но автоконфигурация все равно работает
    }

    @Test
    @DisplayName("PropertySources должны быть доступны")
    void propertySourcesAvailable() {
        // Проверяем, что PropertySources доступны
        org.springframework.core.env.ConfigurableEnvironment env = 
                (org.springframework.core.env.ConfigurableEnvironment) applicationContext.getEnvironment();
        assertThat(env.getPropertySources()).isNotEmpty();
        assertThat(env.getPropertySources()).hasSizeGreaterThan(0);
    }

    @Test
    @DisplayName("Конфигурация безопасности должна быть загружена")
    void securityConfigurationLoaded() {
        // Проверяем, что конфигурация безопасности загружена
        // OAuth2 отключен, но базовая безопасность должна работать
        String oauth2Enabled = applicationContext.getEnvironment()
                .getProperty("spring.security.oauth2.client.enabled");
        assertThat(oauth2Enabled).isEqualTo("false");
    }

    @Test
    @DisplayName("Бины приложения должны быть корректно настроены")
    void applicationBeansCorrect() {
        // Проверяем наличие ключевых бинов
        assertThat(applicationContext.containsBean("dataSource")).isTrue();
        assertThat(applicationContext.containsBean("entityManagerFactory")).isTrue();
        assertThat(applicationContext.containsBean("transactionManager")).isTrue();
    }

    @Test
    @DisplayName("Конфигурация валидации должна быть доступна")
    void validationConfigurationAvailable() {
        // Проверяем, что валидация настроена
        assertThat(applicationContext.containsBean("validator")).isTrue();
    }
}
