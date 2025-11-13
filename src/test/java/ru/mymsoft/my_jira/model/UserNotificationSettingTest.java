package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserNotificationSettingTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        when(user.getId()).thenReturn(1L);
    }

    @Test
    @DisplayName("Should create UserNotificationSetting with builder pattern")
    void shouldCreateUserNotificationSettingWithBuilder() {
        // When
        UserNotificationSetting setting = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("ISSUE_CREATED_EMAIL")
                .isEnabled(true)
                .build();

        // Then
        assertNotNull(setting);
        assertEquals(1L, setting.getId());
        assertEquals(user, setting.getUser());
        assertEquals("ISSUE_CREATED_EMAIL", setting.getNotificationType());
        assertTrue(setting.getIsEnabled());
    }

    @Test
    @DisplayName("Should create UserNotificationSetting with no-args constructor")
    void shouldCreateUserNotificationSettingWithNoArgsConstructor() {
        // When
        UserNotificationSetting setting = new UserNotificationSetting();

        // Then
        assertNotNull(setting);
        assertNull(setting.getId());
        assertNull(setting.getUser());
        assertNull(setting.getNotificationType());
        assertTrue(setting.getIsEnabled()); // Default value
    }

    @Test
    @DisplayName("Should create UserNotificationSetting with all-args constructor")
    void shouldCreateUserNotificationSettingWithAllArgsConstructor() {
        // When
        UserNotificationSetting setting = new UserNotificationSetting(
                1L,
                user,
                "COMMENT_ADDED_IN_APP",
                false
        );

        // Then
        assertNotNull(setting);
        assertEquals(1L, setting.getId());
        assertEquals(user, setting.getUser());
        assertEquals("COMMENT_ADDED_IN_APP", setting.getNotificationType());
        assertFalse(setting.getIsEnabled());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        UserNotificationSetting setting = new UserNotificationSetting();
        User newUser = mock(User.class);

        // When
        setting.setId(5L);
        setting.setUser(newUser);
        setting.setNotificationType("STATUS_CHANGED_SLACK");
        setting.setIsEnabled(false);

        // Then
        assertEquals(5L, setting.getId());
        assertEquals(newUser, setting.getUser());
        assertEquals("STATUS_CHANGED_SLACK", setting.getNotificationType());
        assertFalse(setting.getIsEnabled());
    }

    @Test
    @DisplayName("Should have default value for isEnabled field")
    void shouldHaveDefaultValueForIsEnabled() {
        // When - using no-args constructor
        UserNotificationSetting setting = new UserNotificationSetting();

        // Then
        assertTrue(setting.getIsEnabled());

        // When - using builder without explicit isEnabled
        UserNotificationSetting setting2 = UserNotificationSetting.builder()
                .user(user)
                .notificationType("TEST_NOTIFICATION")
                .build();

        // Then
        assertTrue(setting2.getIsEnabled());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id")
    void shouldImplementEqualsAndHashCodeBasedOnId() {
        // Given
        UserNotificationSetting setting1 = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("ISSUE_CREATED")
                .isEnabled(true)
                .build();

        UserNotificationSetting setting2 = UserNotificationSetting.builder()
                .id(1L)
                .user(mock(User.class)) // Different user
                .notificationType("COMMENT_ADDED") // Different type
                .isEnabled(false) // Different enabled status
                .build();

        UserNotificationSetting setting3 = UserNotificationSetting.builder()
                .id(2L) // Different ID
                .user(user)
                .notificationType("ISSUE_CREATED")
                .isEnabled(true)
                .build();

        UserNotificationSetting setting4 = new UserNotificationSetting(); // null fields

        // Then
        // Same ID should be equal regardless of other fields
        assertEquals(setting1, setting2);
        assertEquals(setting1.hashCode(), setting2.hashCode());

        // Different ID should not be equal
        assertNotEquals(setting1, setting3);

        // Null comparison
        assertNotEquals(setting1, null);
        assertNotEquals(setting1, "some string");
        assertNotEquals(setting1, setting4);

        // Reflexivity
        assertEquals(setting1, setting1);

        // Symmetry
        assertEquals(setting1, setting2);
        assertEquals(setting2, setting1);

        // Consistency
        assertEquals(setting1.hashCode(), setting1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        UserNotificationSetting setting1 = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("TYPE_A")
                .isEnabled(true)
                .build();

        UserNotificationSetting setting2 = UserNotificationSetting.builder()
                .id(1L)
                .user(mock(User.class)) // Different user
                .notificationType("TYPE_B") // Different type
                .isEnabled(false) // Different enabled
                .build(); // Equal to setting1 (same ID)

        UserNotificationSetting setting3 = UserNotificationSetting.builder()
                .id(2L)
                .user(user)
                .notificationType("TYPE_C")
                .isEnabled(true)
                .build(); // Different

        Set<UserNotificationSetting> set = new HashSet<>();

        // When
        set.add(setting1);
        set.add(setting2); // Duplicate (same ID)
        set.add(setting3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by ID");
        assertTrue(set.contains(setting1));
        assertTrue(set.contains(setting2));
        assertTrue(set.contains(setting3));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should handle null and empty notification types")
    void shouldHandleNullAndEmptyNotificationTypes(String notificationType) {
        // Given
        UserNotificationSetting setting = new UserNotificationSetting();

        // When
        setting.setNotificationType(notificationType);

        // Then
        assertEquals(notificationType, setting.getNotificationType());
    }

    @Test
    @DisplayName("Should handle different notification types")
    void shouldHandleDifferentNotificationTypes() {
        // Given
        String[] notificationTypes = {
            "ISSUE_CREATED_EMAIL",
            "COMMENT_ADDED_IN_APP",
            "STATUS_CHANGED_SLACK",
            "ASSIGNEE_CHANGED_PUSH",
            "DEADLINE_APPROACHING_SMS",
            "PROJECT_UPDATED_WEBHOOK"
        };

        // When & Then
        for (int i = 0; i < notificationTypes.length; i++) {
            UserNotificationSetting setting = UserNotificationSetting.builder()
                    .id((long) (i + 1))
                    .user(user)
                    .notificationType(notificationTypes[i])
                    .isEnabled(i % 2 == 0) // Alternate between true/false
                    .build();

            assertEquals(notificationTypes[i], setting.getNotificationType());
            assertEquals(i % 2 == 0, setting.getIsEnabled());
        }
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        UserNotificationSetting setting = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("ISSUE_CREATED")
                .isEnabled(true)
                .build();

        // When
        String toStringResult = setting.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("UserNotificationSetting") ||
                  toStringResult.contains("ISSUE_CREATED") ||
                  toStringResult.contains("isEnabled=true"));
    }

    @Test
    @DisplayName("Should create setting with only required fields")
    void shouldCreateSettingWithOnlyRequiredFields() {
        // When
        UserNotificationSetting setting = UserNotificationSetting.builder()
                .user(user)
                .notificationType("MINIMAL_NOTIFICATION")
                .build();

        // Then
        assertNotNull(setting);
        assertEquals(user, setting.getUser());
        assertEquals("MINIMAL_NOTIFICATION", setting.getNotificationType());
        assertTrue(setting.getIsEnabled()); // Default value
        assertNull(setting.getId());
    }

    @ParameterizedTest
    @CsvSource({
        "ISSUE_CREATED_EMAIL, true, 'Email notification when issue is created'",
        "COMMENT_ADDED_IN_APP, false, 'In-app notification when comment is added'",
        "STATUS_CHANGED_SLACK, true, 'Slack notification when status changes'",
        "ASSIGNEE_CHANGED_PUSH, false, 'Push notification when assignee changes'",
        "DEADLINE_APPROACHING_SMS, true, 'SMS notification when deadline approaches'"
    })
    @DisplayName("Should create different notification settings with various properties")
    void shouldCreateDifferentNotificationSettings(String notificationType, boolean isEnabled, String description) {
        // When
        UserNotificationSetting setting = UserNotificationSetting.builder()
                .user(user)
                .notificationType(notificationType)
                .isEnabled(isEnabled)
                .build();

        // Then
        assertEquals(notificationType, setting.getNotificationType());
        assertEquals(isEnabled, setting.getIsEnabled());
        assertEquals(user, setting.getUser());
    }

    @Test
    @DisplayName("Should handle notification type length constraints")
    void shouldHandleNotificationTypeLengthConstraints() {
        // Given - notification type with maximum allowed length (50 chars)
        String maxLengthType = "N".repeat(50);

        // When
        UserNotificationSetting setting = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType(maxLengthType)
                .isEnabled(true)
                .build();

        // Then
        assertEquals(maxLengthType, setting.getNotificationType());
        assertEquals(50, setting.getNotificationType().length());
    }

    @Test
    @DisplayName("Should toggle notification setting")
    void shouldToggleNotificationSetting() {
        // Given
        UserNotificationSetting setting = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("TOGGLE_TEST")
                .isEnabled(true)
                .build();

        // When - disable
        setting.setIsEnabled(false);

        // Then
        assertFalse(setting.getIsEnabled());

        // When - enable again
        setting.setIsEnabled(true);

        // Then
        assertTrue(setting.getIsEnabled());
    }

    @Test
    @DisplayName("Should handle multiple settings for same user")
    void shouldHandleMultipleSettingsForSameUser() {
        // Given
        UserNotificationSetting emailSetting = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("ISSUE_CREATED_EMAIL")
                .isEnabled(true)
                .build();

        UserNotificationSetting pushSetting = UserNotificationSetting.builder()
                .id(2L)
                .user(user)
                .notificationType("COMMENT_ADDED_PUSH")
                .isEnabled(false)
                .build();

        UserNotificationSetting slackSetting = UserNotificationSetting.builder()
                .id(3L)
                .user(user)
                .notificationType("STATUS_CHANGED_SLACK")
                .isEnabled(true)
                .build();

        // Then
        assertAll(
                () -> assertEquals(user, emailSetting.getUser()),
                () -> assertEquals("ISSUE_CREATED_EMAIL", emailSetting.getNotificationType()),
                () -> assertTrue(emailSetting.getIsEnabled()),
                () -> assertEquals(user, pushSetting.getUser()),
                () -> assertEquals("COMMENT_ADDED_PUSH", pushSetting.getNotificationType()),
                () -> assertFalse(pushSetting.getIsEnabled()),
                () -> assertEquals(user, slackSetting.getUser()),
                () -> assertEquals("STATUS_CHANGED_SLACK", slackSetting.getNotificationType()),
                () -> assertTrue(slackSetting.getIsEnabled())
        );
    }

    @Test
    @DisplayName("Should handle same notification type for different users")
    void shouldHandleSameNotificationTypeForDifferentUsers() {
        // Given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn(1L);

        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(2L);

        // When
        UserNotificationSetting user1Setting = UserNotificationSetting.builder()
                .id(1L)
                .user(user1)
                .notificationType("ISSUE_CREATED_EMAIL")
                .isEnabled(true)
                .build();

        UserNotificationSetting user2Setting = UserNotificationSetting.builder()
                .id(2L)
                .user(user2)
                .notificationType("ISSUE_CREATED_EMAIL")
                .isEnabled(false)
                .build();

        // Then
        assertEquals("ISSUE_CREATED_EMAIL", user1Setting.getNotificationType());
        assertEquals("ISSUE_CREATED_EMAIL", user2Setting.getNotificationType());
        assertEquals(user1, user1Setting.getUser());
        assertEquals(user2, user2Setting.getUser());
        assertTrue(user1Setting.getIsEnabled());
        assertFalse(user2Setting.getIsEnabled());
    }

    @Test
    @DisplayName("Should update notification setting properties")
    void shouldUpdateNotificationSettingProperties() {
        // Given
        UserNotificationSetting setting = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("OLD_TYPE")
                .isEnabled(false)
                .build();

        User newUser = mock(User.class);

        // When
        setting.setUser(newUser);
        setting.setNotificationType("NEW_TYPE");
        setting.setIsEnabled(true);

        // Then
        assertEquals(newUser, setting.getUser());
        assertEquals("NEW_TYPE", setting.getNotificationType());
        assertTrue(setting.getIsEnabled());
    }

    @Test
    @DisplayName("Should handle realistic notification scenarios")
    void shouldHandleRealisticNotificationScenarios() {
        // Given - Realistic notification settings for a JIRA-like system
        UserNotificationSetting issueCreated = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("ISSUE_CREATED_EMAIL")
                .isEnabled(true)
                .build();

        UserNotificationSetting commentAdded = UserNotificationSetting.builder()
                .id(2L)
                .user(user)
                .notificationType("COMMENT_ADDED_IN_APP")
                .isEnabled(true)
                .build();

        UserNotificationSetting statusChanged = UserNotificationSetting.builder()
                .id(3L)
                .user(user)
                .notificationType("STATUS_CHANGED_SLACK")
                .isEnabled(false)
                .build();

        UserNotificationSetting assigneeChanged = UserNotificationSetting.builder()
                .id(4L)
                .user(user)
                .notificationType("ASSIGNEE_CHANGED_PUSH")
                .isEnabled(true)
                .build();

        UserNotificationSetting deadlineApproaching = UserNotificationSetting.builder()
                .id(5L)
                .user(user)
                .notificationType("DEADLINE_APPROACHING_SMS")
                .isEnabled(false)
                .build();

        // Then
        assertAll(
                () -> assertEquals("ISSUE_CREATED_EMAIL", issueCreated.getNotificationType()),
                () -> assertTrue(issueCreated.getIsEnabled()),
                () -> assertEquals("COMMENT_ADDED_IN_APP", commentAdded.getNotificationType()),
                () -> assertTrue(commentAdded.getIsEnabled()),
                () -> assertEquals("STATUS_CHANGED_SLACK", statusChanged.getNotificationType()),
                () -> assertFalse(statusChanged.getIsEnabled()),
                () -> assertEquals("ASSIGNEE_CHANGED_PUSH", assigneeChanged.getNotificationType()),
                () -> assertTrue(assigneeChanged.getIsEnabled()),
                () -> assertEquals("DEADLINE_APPROACHING_SMS", deadlineApproaching.getNotificationType()),
                () -> assertFalse(deadlineApproaching.getIsEnabled())
        );
    }

    @Test
    @DisplayName("Should verify unique constraint simulation")
    void shouldVerifyUniqueConstraintSimulation() {
        // Given
        UserNotificationSetting setting1 = UserNotificationSetting.builder()
                .id(1L)
                .user(user)
                .notificationType("DUPLICATE_TYPE")
                .isEnabled(true)
                .build();

        UserNotificationSetting setting2 = UserNotificationSetting.builder()
                .id(2L) // Different ID
                .user(user) // Same user
                .notificationType("DUPLICATE_TYPE") // Same notification type
                .isEnabled(false) // Different enabled status
                .build();

        // Then - They are different entities due to different IDs
        assertNotEquals(setting1, setting2);
        assertEquals(setting1.getUser(), setting2.getUser());
        assertEquals(setting1.getNotificationType(), setting2.getNotificationType());

        // This demonstrates why the unique constraint is needed in database
        // (user_id + notification_type should be unique)
    }
}
