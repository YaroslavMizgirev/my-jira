package ru.mymsoft.my_jira;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = MyJiraApplication.class)
@ActiveProfiles("test")
class MyJiraApplicationIntegrationTest {

    // @MockBean
    // Можно добавить моки для бинов, если они мешают тестированию
    // private SomeService someService;

    @Test
    void applicationStartsWithoutErrors() {
        // Тест проходит, если приложение запускается без исключений
        assertDoesNotThrow(() -> {
            // Контекст загружается в @SpringBootTest
        });
    }
}
