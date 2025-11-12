package ru.mymsoft.my_jira;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MyJiraApplicationUnitTest {
    @Test
    void testMainClassCreation() {
        // Простая проверка, что класс можно создать
        MyJiraApplication application = new MyJiraApplication();
        assertNotNull(application);
    }

    @Test
    void testMainMethodExists() {
        // Проверяем, что метод main существует и доступен
        assertNotNull(MyJiraApplication.class.getDeclaredMethod("main", String[].class));
    }
}
