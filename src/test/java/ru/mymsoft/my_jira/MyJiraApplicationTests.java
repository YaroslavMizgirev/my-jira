package ru.mymsoft.my_jira;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.jpa.hibernate.ddl-auto=create-drop"
})
class MyJiraApplicationTest {
	@Test
	void contextLoads() {
		// Тест проходит, если Spring контекст загружается без ошибок
		assertThat(true).isTrue(); // Простая проверка, что тест выполняется
	}

	@Test
	void mainMethodStartsApplication() {
		// Проверяем, что основной метод не выбрасывает исключений
		assertDoesNotThrow(() -> MyJiraApplication.main(new String[] {}));
	}

	@Test
	void mainMethodStartsAndLogsExpectedMessages(CapturedOutput output) {
		// Когда
		assertDoesNotThrow(() -> MyJiraApplication.main(new String[] {}));

		// Тогда
		assertThat(output).contains("Starting MyJiraApplication");
		assertThat(output).contains("Started MyJiraApplication");
	}

	@Test
	void mainMethodIsAccessibleAndRunnable() {
		// Просто проверяем что метод существует и доступен
		assertDoesNotThrow(() -> {
			Method mainMethod = MyJiraApplication.class.getMethod("main", String[].class);
			assertThat(mainMethod).isNotNull();
			assertThat(mainMethod.getReturnType()).isEqualTo(void.class);
		});
	}
}
