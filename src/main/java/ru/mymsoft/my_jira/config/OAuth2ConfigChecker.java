package ru.mymsoft.my_jira.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OAuth2ConfigChecker {
    @Bean
    public CommandLineRunner checkOAuth2Config() {
        return args -> {
            log.info("=== Проверка конфигурации OAuth2 провайдеров ===");
            
            checkProvider("Google", System.getenv("GOOGLE_CLIENT_ID"));
            checkProvider("GitHub", System.getenv("GITHUB_CLIENT_ID"));
            checkProvider("GitLab", System.getenv("GITLAB_CLIENT_ID"));
            checkProvider("Yandex", System.getenv("YANDEX_CLIENT_ID"));
            checkProvider("Mail.ru", System.getenv("MAILRU_CLIENT_ID"));
            
            log.info("=== Проверка завершена ===");
        };
    }
    
    private void checkProvider(String providerName, String clientId) {
        if (clientId == null || clientId.isEmpty() || "disabled".equals(clientId)) {
            log.warn("❌ OAuth2 провайдер '{}' отключен (не установлены переменные окружения)", providerName);
        } else {
            log.info("✅ OAuth2 провайдер '{}' настроен", providerName);
        }
    }
}