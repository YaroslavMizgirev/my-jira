package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testUserCreationWithSetters() {
        User user = User.builder()
                .email("johndoe@example.com")
                .username("johndoe")
                .build();
        user.setPasswordHash("hashed_password_123");

        assertThat(user.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(user.getUsername()).isEqualTo("johndoe");
        assertThat(user.getPasswordHash()).isEqualTo("hashed_password_123");
    }

    @Test
    void testUserBuilder() {
        User user = User.builder()
                .id(1L)
                .email("johndoe@example.com")
                .username("johndoe")
                .passwordHash("hashed_password_123")
                .oauthProvider("github")
                .displayName("John Doe")
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(user.getUsername()).isEqualTo("johndoe");
        assertThat(user.getPasswordHash()).isEqualTo("hashed_password_123");
        assertThat(user.getOauthProvider()).isEqualTo("github");
        assertThat(user.getDisplayName()).isEqualTo("John Doe");
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();

        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getPasswordHash()).isNull();
    }

    @Test
    void testEqualsAndHashCode() {
        // equals/hashCode по email + username (оба @EqualsAndHashCode.Include)
        User user1 = User.builder().email("user@test.com").username("user1").build();
        User user2 = User.builder().email("user@test.com").username("user1").build();
        User user3 = User.builder().email("other@test.com").username("user3").build();

        assertThat(user1).isEqualTo(user2);
        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1).hasSameHashCodeAs(user2);
    }

    @Test
    void testToString() {
        User user = User.builder()
                .id(1L)
                .email("johndoe@example.com")
                .username("johndoe")
                .passwordHash("secret_hash")
                .build();

        String toString = user.toString();

        assertThat(toString)
                .contains("id=1")
                .contains("email=johndoe@example.com")
                .contains("username=johndoe")
                .doesNotContain("secret_hash"); // passwordHash excluded from toString
    }

    @Test
    void testFieldAnnotationsRespected() {
        User user = User.builder()
                .email("testuser@example.com")
                .username("testuser")
                .passwordHash("hash")
                .build();

        assertThat(user)
                .hasFieldOrPropertyWithValue("email", "testuser@example.com")
                .hasFieldOrPropertyWithValue("username", "testuser")
                .hasFieldOrPropertyWithValue("passwordHash", "hash");
    }

    @Test
    void testNullSafety() {
        User user = new User();
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getPasswordHash()).isNull();
    }

    @Test
    void testUniqueConstraintsRepresentation() {
        User user1 = User.builder().email("same@email.com").username("user1").build();
        User user2 = User.builder().email("same@email.com").username("user2").build();

        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getUsername()).isNotEqualTo(user2.getUsername());
    }
}
