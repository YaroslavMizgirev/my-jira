package ru.mymsoft.my_jira;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class MyJiraApplicationProfileTest {

    @Test
    void applicationStartsWithTestProfile() {
        // Тест проверяет запуск с определенным профилем
        assertTrue(true, "Application should start with test profile");
    }
}
