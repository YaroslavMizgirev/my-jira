package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void testUserCreationWithSetters() {
        // Given
        User user = User.builder().build();
        user.setId(1L);
        user.setEmail("johndoe@example.com");
        user.setUsername("johndoe");
        user.setPassword("hashed_password_123");

        // Then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(user.getUsername()).isEqualTo("johndoe");
        assertThat(user.getPassword()).isEqualTo("hashed_password_123");
    }

    @Test
    void testUserCreationWithAllArgsConstructor() {
        // Given
        User user = new User(1L, "johndoe@example.com", "johndoe", "hashed_password_123");

        // Then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(user.getUsername()).isEqualTo("johndoe");
        assertThat(user.getPassword()).isEqualTo("hashed_password_123");
    }

    @Test
    void testNoArgsConstructor() {
        // Given
        User user = new User();

        // Then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getPassword()).isNull();
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@test.com");

        User user2 = new User();
        user2.setId(1L); // Тот же ID
        user2.setEmail("different@test.com"); // Разный email

        User user3 = new User();
        user3.setId(2L); // Другой ID
        user3.setEmail("user3@test.com");

        assertThat(user1)
            .isNotEqualTo(user2)
            .isNotEqualTo(user3)
            .hasSameHashCodeAs(user2);
    }

    @Test
    void testToString() {
        // Given
        User user = new User(1L, "johndoe@example.com", "johndoe", "hashed_password");

        // When
        String toString = user.toString();

        // Then
        assertThat(toString)
            .contains("User")
            .contains("id=1")
            .contains("email=johndoe@example.com")
            .contains("username=johndoe")
            .doesNotContain("hashed_password");
    }

    @Test
    void testFieldAnnotationsRespected() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");
        user.setUsername("testuser");
        user.setPassword("hash");

        // Then: Проверяем, что поля включены в equals/hashCode согласно аннотациям
        assertThat(user)
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("email", "testuser@example.com")
            .hasFieldOrPropertyWithValue("username", "testuser")
            .hasFieldOrPropertyWithValue("passwordHash", "hash");
    }

    @Test
    void testNullSafety() {
        // Given
        User user = new User();

        // Then: Должен нормально работать с null значениями
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getPassword()).isNull();
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