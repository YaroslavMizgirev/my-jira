package ru.mymsoft.my_jira;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Базовый тест для проверки запуска приложения MyJiraApplication.
 * Использует тестовые свойства для избежания проблем с OAuth2 конфигурацией.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
    "spring.security.oauth2.client.enabled=false",
    "spring.main.allow-bean-definition-overriding=true"
})
@DisplayName("MyJiraApplication Basic Tests")
class MyJiraApplicationBasicTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Spring контекст должен успешно загружаться")
    void contextLoads() {
        // Проверяем, что контекст Spring создан без ошибок
        assertThat(applicationContext).isNotNull();
        assertThat(applicationContext.getBeanDefinitionCount()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Основной класс приложения должен быть в контексте")
    void mainApplicationBeanPresent() {
        // Проверяем, что основной класс приложения зарегистрирован
        assertThat(applicationContext.containsBean("myJiraApplication")).isTrue();
    }

    @Test
    @DisplayName("Все необходимые бины должны быть доступны")
    void requiredBeansPresent() {
        // Проверяем наличие ключевых бинов приложения
        assertThat(applicationContext.containsBean("dataSource")).isTrue();
        assertThat(applicationContext.containsBean("entityManagerFactory")).isTrue();
        assertThat(applicationContext.containsBean("transactionManager")).isTrue();
    }

    @Test
    @DisplayName("Конфигурация JPA должна быть корректной")
    void jpaConfigurationCorrect() {
        // Проверяем, что JPA конфигурация загружена
        assertThat(applicationContext.containsBean("jpaVendorAdapter")).isTrue();
        assertThat(applicationContext.containsBean("jpaDialect")).isTrue();
    }

    @Test
    @DisplayName("Репозитории должны быть обнаружены")
    void repositoriesDiscovered() {
        // Проверяем, что репозитории были обнаружены Spring Data
        String[] repositoryBeans = applicationContext.getBeanNamesForType(org.springframework.data.repository.Repository.class);
        assertThat(repositoryBeans).isNotEmpty();
        assertThat(repositoryBeans.length).isGreaterThan(0);
    }
}
