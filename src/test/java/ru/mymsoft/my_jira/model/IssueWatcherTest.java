package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class IssueWatcherTest {

    @Test
    @DisplayName("Should create IssueWatcher with composite key")
    void shouldCreateIssueWatcherWithCompositeKey() {
        // Given
        Issue issue = mock(Issue.class);
        User user = mock(User.class);

        // When
        IssueWatcher watcher = IssueWatcher.builder()
                .issue(issue)
                .user(user)
                .build();

        // Then
        assertNotNull(watcher);
        assertEquals(issue, watcher.getIssue());
        assertEquals(user, watcher.getUser());
    }

    @Test
    @DisplayName("Should create IssueWatcher using no-args constructor")
    void shouldCreateIssueWatcherUsingNoArgsConstructor() {
        // When
        IssueWatcher watcher = new IssueWatcher();

        // Then
        assertNotNull(watcher);
        assertNull(watcher.getIssue());
        assertNull(watcher.getUser());
    }

    @Test
    @DisplayName("Should create IssueWatcher using all-args constructor")
    void shouldCreateIssueWatcherUsingAllArgsConstructor() {
        // Given
        Issue issue = mock(Issue.class);
        User user = mock(User.class);

        // When
        IssueWatcher watcher = new IssueWatcher(issue, user);

        // Then
        assertNotNull(watcher);
        assertEquals(issue, watcher.getIssue());
        assertEquals(user, watcher.getUser());
    }

    @Test
    @DisplayName("Should set and get properties correctly")
    void shouldSetAndGetPropertiesCorrectly() {
        // Given
        IssueWatcher watcher = new IssueWatcher();
        Issue issue = mock(Issue.class);
        User user = mock(User.class);

        // When
        watcher.setIssue(issue);
        watcher.setUser(user);

        // Then
        assertEquals(issue, watcher.getIssue());
        assertEquals(user, watcher.getUser());
    }

    @Test
    @DisplayName("IssueWatcherId should implement equals and hashCode correctly")
    void issueWatcherIdShouldImplementEqualsAndHashCode() {
        // Given
        IssueWatcher.IssueWatcherId id1 = new IssueWatcher.IssueWatcherId(1L, 2L);
        IssueWatcher.IssueWatcherId id2 = new IssueWatcher.IssueWatcherId(1L, 2L);
        IssueWatcher.IssueWatcherId id3 = new IssueWatcher.IssueWatcherId(1L, 3L);
        IssueWatcher.IssueWatcherId id4 = new IssueWatcher.IssueWatcherId(3L, 2L);

        // Then
        // Test equals
        assertEquals(id1, id2, "Same issue and user IDs should be equal");
        assertNotEquals(id1, id3, "Different user IDs should not be equal");
        assertNotEquals(id1, id4, "Different issue IDs should not be equal");
        assertNotEquals(id1, null, "Should not equal null");
        assertNotEquals(id1, "some string", "Should not equal different type");

        // Test hashCode
        assertEquals(id1.hashCode(), id2.hashCode(), "Equal objects should have same hash code");
        assertNotEquals(id1.hashCode(), id3.hashCode(), "Different objects should have different hash codes");

        // Test consistency
        assertEquals(id1.hashCode(), id1.hashCode(), "Hash code should be consistent");
    }

    @Test
    @DisplayName("IssueWatcherId should work correctly in collections")
    void issueWatcherIdShouldWorkCorrectlyInCollections() {
        // Given
        IssueWatcher.IssueWatcherId id1 = new IssueWatcher.IssueWatcherId(1L, 2L);
        IssueWatcher.IssueWatcherId id2 = new IssueWatcher.IssueWatcherId(1L, 2L);
        IssueWatcher.IssueWatcherId id3 = new IssueWatcher.IssueWatcherId(1L, 3L);

        Set<IssueWatcher.IssueWatcherId> set = new HashSet<>();

        // When
        set.add(id1);
        set.add(id2); // duplicate
        set.add(id3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates");
        assertTrue(set.contains(id1));
        assertTrue(set.contains(id2));
        assertTrue(set.contains(id3));
    }

    @Test
    @DisplayName("IssueWatcherId should have working getters and setters")
    void issueWatcherIdShouldHaveWorkingGettersAndSetters() {
        // Given
        IssueWatcher.IssueWatcherId id = new IssueWatcher.IssueWatcherId();

        // When
        id.setIssue(5L);
        id.setUser(10L);

        // Then
        assertEquals(5L, id.getIssue());
        assertEquals(10L, id.getUser());
    }

    @Test
    @DisplayName("IssueWatcherId should implement Serializable")
    void issueWatcherIdShouldImplementSerializable() {
        // Given
        IssueWatcher.IssueWatcherId id = new IssueWatcher.IssueWatcherId(1L, 2L);

        // Then
        assertTrue(id instanceof java.io.Serializable, "IssueWatcherId should implement Serializable");
    }

    @Test
    @DisplayName("Should create IssueWatcherId with all-args constructor")
    void shouldCreateIssueWatcherIdWithAllArgsConstructor() {
        // When
        IssueWatcher.IssueWatcherId id = new IssueWatcher.IssueWatcherId(1L, 2L);

        // Then
        assertEquals(1L, id.getIssue());
        assertEquals(2L, id.getUser());
    }

    @Test
    @DisplayName("Should test toString method for IssueWatcher")
    void shouldTestToStringMethod() {
        // Given
        Issue issue = mock(Issue.class);
        User user = mock(User.class);
        IssueWatcher watcher = new IssueWatcher(issue, user);

        // When
        String toStringResult = watcher.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("Should handle null values in composite key")
    void shouldHandleNullValuesInCompositeKey() {
        // Given
        IssueWatcher.IssueWatcherId id1 = new IssueWatcher.IssueWatcherId(null, 2L);
        IssueWatcher.IssueWatcherId id2 = new IssueWatcher.IssueWatcherId(null, 2L);
        IssueWatcher.IssueWatcherId id3 = new IssueWatcher.IssueWatcherId(1L, null);

        // Then
        assertEquals(id1, id2, "Both null issue IDs should be considered equal");
        assertNotEquals(id1, id3, "Different null patterns should not be equal");
    }
}
