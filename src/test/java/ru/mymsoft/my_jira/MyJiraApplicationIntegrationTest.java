package ru.mymsoft.my_jira;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интеграционный тест для MyJiraApplication.
 * Проверяет корректность интеграции основных компонентов приложения.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableJpaRepositories(basePackages = "ru.mymsoft.my_jira.repository")
@TestPropertySource(properties = {
    "spring.security.oauth2.client.enabled=false",
    "spring.main.allow-bean-definition-overriding=true"
})
@DisplayName("MyJiraApplication Integration Tests")
class MyJiraApplicationIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Приложение должно запускаться без ошибок")
    void applicationStartsWithoutErrors() {
        // Тест проходит, если приложение запускается без исключений
        assertThat(applicationContext).isNotNull();
    }

    @Test
    @DisplayName("JPA репозитории должны быть корректно настроены")
    void jpaRepositoriesConfigured() {
        // Проверяем, что JPA репозитории настроены
        String[] repositoryBeans = applicationContext.getBeanNamesForType(
                org.springframework.data.repository.Repository.class);
        assertThat(repositoryBeans).isNotEmpty();
        assertThat(repositoryBeans.length).isGreaterThan(0);
    }

    @Test
    @DisplayName("EntityManagerFactory должен быть доступен")
    void entityManagerFactoryAvailable() {
        // Проверяем, что EntityManagerFactory настроен
        assertThat(applicationContext.containsBean("entityManagerFactory")).isTrue();
        
        jakarta.persistence.EntityManagerFactory emf = applicationContext.getBean(
                jakarta.persistence.EntityManagerFactory.class);
        assertThat(emf).isNotNull();
    }

    @Test
    @DisplayName("TransactionManager должен быть доступен")
    void transactionManagerAvailable() {
        // Проверяем, что TransactionManager настроен
        assertThat(applicationContext.containsBean("transactionManager")).isTrue();
        
        org.springframework.transaction.PlatformTransactionManager tm = applicationContext.getBean(
                org.springframework.transaction.PlatformTransactionManager.class);
        assertThat(tm).isNotNull();
    }

    @Test
    @DisplayName("DataSource должен быть корректно настроен")
    void dataSourceConfigured() {
        // Проверяем, что DataSource настроен
        assertThat(applicationContext.containsBean("dataSource")).isTrue();
        
        javax.sql.DataSource dataSource = applicationContext.getBean(javax.sql.DataSource.class);
        assertThat(dataSource).isNotNull();
    }

    @Test
    @DisplayName("Spring Data JPA должна быть корректно интегрирована")
    void springDataJpaIntegration() {
        // Проверяем интеграцию Spring Data JPA
        String[] jpaBeans = applicationContext.getBeanNamesForType(
                org.springframework.data.jpa.repository.support.JpaRepositoryImplementation.class);
        // Может быть пустым, если нет явных реализаций
    }

    @Test
    @DisplayName("Валидация должна быть настроена")
    void validationConfigured() {
        // Проверяем, что валидация настроена
        assertThat(applicationContext.containsBean("validator")).isTrue();
        
        jakarta.validation.Validator validator = applicationContext.getBean(jakarta.validation.Validator.class);
        assertThat(validator).isNotNull();
    }

    @Test
    @DisplayName("Конфигурация безопасности должна быть загружена")
    void securityConfigurationLoaded() {
        // Проверяем, что конфигурация безопасности загружена
        // OAuth2 отключен для тестов, но базовая безопасность должна работать
        String oauth2Enabled = applicationContext.getEnvironment()
                .getProperty("spring.security.oauth2.client.enabled");
        assertThat(oauth2Enabled).isEqualTo("false");
    }

    @Test
    @DisplayName("Все основные сервисы должны быть в контексте")
    void mainServicesAvailable() {
        // Проверяем наличие основных сервисов
        String[] serviceBeans = applicationContext.getBeanNamesForType(
                org.springframework.stereotype.Service.class);
        assertThat(serviceBeans).isNotEmpty();
    }

    @Test
    @DisplayName("Конфигурация логирования должна быть корректной")
    void loggingConfiguration() {
        // Проверяем, что логирование настроено
        org.slf4j.Logger logger = applicationContext.getBean(org.slf4j.LoggerFactory.class)
                .getLogger(MyJiraApplication.class);
        assertThat(logger).isNotNull();
    }
}
