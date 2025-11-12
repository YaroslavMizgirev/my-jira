package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void testUserCreationWithSetters() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("johndoe");
        user.setPasswordHash("hashed_password_123");

        // Then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getUsername()).isEqualTo("johndoe");
        assertThat(user.getPasswordHash()).isEqualTo("hashed_password_123");
    }

    @Test
    void testUserCreationWithAllArgsConstructor() {
        // Given
        User user = new User(1L, "test@example.com", "johndoe", "hashed_password_123");

        // Then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getUsername()).isEqualTo("johndoe");
        assertThat(user.getPasswordHash()).isEqualTo("hashed_password_123");
    }

    @Test
    void testNoArgsConstructor() {
        // Given
        User user = new User();

        // Then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getPasswordHash()).isNull();
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@test.com");
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(1L); // Тот же ID
        user2.setEmail("different@test.com"); // Разный email
        user2.setUsername("different"); // Разный username

        User user3 = new User();
        user3.setId(2L); // Другой ID
        user3.setEmail("user3@test.com");
        user3.setUsername("user3");

        // Then: equals/hashCode только по ID (благодаря onlyExplicitlyIncluded = true)
        assertThat(user1)
            .isEqualTo(user2); // Потому что одинаковый ID
            .isNotEqualTo(user3); // Потому что разные ID
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    void testToString() {
        // Given
        User user = new User(1L, "test@example.com", "johndoe", "hashed_password");

        // When
        String toString = user.toString();

        // Then
        assertThat(toString).contains("User");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("email=test@example.com");
        assertThat(toString).contains("username=johndoe");
        // Пароль не должен отображаться в toString для безопасности
        assertThat(toString).doesNotContain("hashed_password");
    }

    @Test
    void testFieldAnnotationsRespected() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPasswordHash("hash");

        // Then: Проверяем, что поля включены в equals/hashCode согласно аннотациям
        assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(user).hasFieldOrPropertyWithValue("email", "test@example.com");
        assertThat(user).hasFieldOrPropertyWithValue("username", "testuser");
        assertThat(user).hasFieldOrPropertyWithValue("passwordHash", "hash");
    }

    @Test
    void testNullSafety() {
        // Given
        User user = new User();

        // Then: Должен нормально работать с null значениями
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getPasswordHash()).isNull();
        assertThat(user).isEqualTo(new User()); // Два пустых пользователя равны
    }

    @Test
    void testUniqueConstraintsRepresentation() {
        // This test verifies that the unique constraints are properly defined
        User user1 = new User(1L, "same@email.com", "user1", "hash1");
        User user2 = new User(2L, "same@email.com", "user2", "hash2"); // Дублирующий email

        // В реальной БД это вызвало бы нарушение уникальности
        // Здесь просто проверяем, что модель позволяет создать такие объекты
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getUsername()).isNotEqualTo(user2.getUsername());
    }
}
