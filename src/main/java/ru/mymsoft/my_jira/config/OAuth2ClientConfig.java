package ru.mymsoft.my_jira.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;

@Configuration
public class OAuth2ClientConfig {
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            List<org.springframework.security.oauth2.client.registration.ClientRegistration> registrations) {
        
        // Фильтруем только активные провайдеры (не "disabled")
        List<org.springframework.security.oauth2.client.registration.ClientRegistration> activeRegistrations = 
            registrations.stream()
                .filter(reg -> !"disabled".equals(reg.getClientId()))
                .toList();
        
        return new InMemoryClientRegistrationRepository(activeRegistrations);
    }
}