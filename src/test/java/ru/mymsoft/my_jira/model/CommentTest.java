package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Comment comment;
    private User author;
    private Issue issue;
    private Instant testInstant;

    @BeforeEach
    void setUp() {
        testInstant = Instant.now();

        author = new User();
        author.setId(1L);
        author.setUsername("testuser");

        issue = new Issue();
        issue.setId(100L);
        issue.setTitle("Test Issue");

        comment = new Comment();
        comment.setId(1L);
        comment.setAuthor(author);
        comment.setIssue(issue);
        comment.setContent("Test comment content");
        comment.setCreatedAt(testInstant);
        comment.setUpdatedAt(testInstant);
    }

    @Test
    void testCommentCreation() {
        assertNotNull(comment);
        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getContent()).isEqualTo("Test comment content");
        assertThat(comment.getAuthor()).isEqualTo(author);
        assertThat(comment.getIssue()).isEqualTo(issue);
        assertThat(comment.getCreatedAt()).isEqualTo(testInstant);
        assertThat(comment.getUpdatedAt()).isEqualTo(testInstant);
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Comment newComment = new Comment();
        User newAuthor = new User();
        newAuthor.setId(2L);
        Issue newIssue = new Issue();
        newIssue.setId(200L);
        Instant newInstant = Instant.now().plusSeconds(1000);

        // When
        newComment.setId(2L);
        newComment.setAuthor(newAuthor);
        newComment.setIssue(newIssue);
        newComment.setContent("New comment content");
        newComment.setCreatedAt(newInstant);
        newComment.setUpdatedAt(newInstant);

        // Then
        assertThat(newComment.getId()).isEqualTo(2L);
        assertThat(newComment.getAuthor()).isEqualTo(newAuthor);
        assertThat(newComment.getIssue()).isEqualTo(newIssue);
        assertThat(newComment.getContent()).isEqualTo("New comment content");
        assertThat(newComment.getCreatedAt()).isEqualTo(newInstant);
        assertThat(newComment.getUpdatedAt()).isEqualTo(newInstant);
    }

    @Test
    void testEqualsAndHashCode() {
        Comment comment1 = new Comment();
        comment1.setId(1L);

        Comment comment2 = new Comment();
        comment2.setId(1L);

        Comment comment3 = new Comment();
        comment3.setId(2L);

        assertThat(comment1)
          .isEqualTo(comment2)
          .isNotEqualTo(comment3)
          .isNotEqualTo(null)
          .isNotEqualTo("string")
          .hasSameHashCodeAs(comment2);
        assertThat(comment1.hashCode()).isNotEqualTo(comment3.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        Comment emptyComment = new Comment();
        assertNotNull(emptyComment);
        assertNull(emptyComment.getId());
        assertNull(emptyComment.getContent());
        assertNull(emptyComment.getAuthor());
        assertNull(emptyComment.getIssue());
        assertNull(emptyComment.getCreatedAt());
        assertNull(emptyComment.getUpdatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        Comment fullComment = new Comment(1L, issue, author, "Test content", testInstant, testInstant);

        assertThat(fullComment.getId()).isEqualTo(1L);
        assertThat(fullComment.getIssue()).isEqualTo(issue);
        assertThat(fullComment.getAuthor()).isEqualTo(author);
        assertThat(fullComment.getContent()).isEqualTo("Test content");
        assertThat(fullComment.getCreatedAt()).isEqualTo(testInstant);
        assertThat(fullComment.getUpdatedAt()).isEqualTo(testInstant);
    }

    @Test
    void testToString() {
        String toString = comment.toString();
        assertThat(toString)
          .contains("Comment")
          .contains("id=1");
    }
}
