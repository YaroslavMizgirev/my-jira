package ru.mymsoft.my_jira;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MyJiraApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void applicationContextLoads() {
        // Проверяем, что контекст Spring создан
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void mainBeanIsPresent() {
        // Проверяем, что основной класс зарегистрирован в контексте
        assertThat(applicationContext.containsBean("myJiraApplication")).isTrue();
    }
}
