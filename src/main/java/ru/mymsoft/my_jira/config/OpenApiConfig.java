package ru.mymsoft.my_jira.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Jira API")
                        .description("""
                            REST API для планировщика
                        """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("mymsoft")
                                .email("yaroslav@mizgirev.ru")
                                .url("https://mymsoft.ru"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url(contextPath)
                                .description("Основной сервер"),
                        new Server()
                                .url("http://localhost:8080" + contextPath)
                                .description("Локальный сервер")
                ));
    }
}