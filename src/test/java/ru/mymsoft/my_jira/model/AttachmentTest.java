package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.Instant;
import static org.assertj.core.api.Assertions.assertThat;

class AttachmentTest {

    private Attachment attachment;
    private Issue issue;
    private User uploader;
    private FileType fileType;

    @BeforeEach
    void setUp() {
        issue = new Issue();
        issue.setId(1L);
        
        uploader = new User();
        uploader.setId(1L);
        
        fileType = new FileType();
        fileType.setId(1L);
        
        attachment = new Attachment();
        attachment.setId(1L);
        attachment.setIssue(issue);
        attachment.setUploader(uploader);
        attachment.setFileName("document.pdf");
        attachment.setFileType(fileType);
        attachment.setFileSizeBytes(1024L);
        attachment.setStoragePath("/uploads/2024/document.pdf");
        attachment.setDescription("Project requirements document");
        attachment.setCreatedAt(Instant.now());
    }

    @Test
    void testAttachmentCreation() {
        // Then
        assertThat(attachment.getId()).isEqualTo(1L);
        assertThat(attachment.getIssue()).isEqualTo(issue);
        assertThat(attachment.getUploader()).isEqualTo(uploader);
        assertThat(attachment.getFileName()).isEqualTo("document.pdf");
        assertThat(attachment.getFileType()).isEqualTo(fileType);
        assertThat(attachment.getFileSizeBytes()).isEqualTo(1024L);
        assertThat(attachment.getStoragePath()).isEqualTo("/uploads/2024/document.pdf");
        assertThat(attachment.getDescription()).isEqualTo("Project requirements document");
        assertThat(attachment.getCreatedAt()).isNotNull();
    }

    @Test
    void testAttachmentWithDescription() {
        // Given
        attachment.setDescription("Updated design mockups for review");
        
        // Then
        assertThat(attachment.getDescription()).isEqualTo("Updated design mockups for review");
        assertThat(attachment.getDescription()).contains("mockups");
    }

    @Test
    void testAttachmentWithNullDescription() {
        // Given
        attachment.setDescription(null);
        
        // Then
        assertThat(attachment.getDescription()).isNull();
        assertThat(attachment.getFileName()).isEqualTo("document.pdf"); // другие поля не затронуты
    }

    @Test
    void testAttachmentWithEmptyDescription() {
        // Given
        attachment.setDescription("");
        
        // Then
        assertThat(attachment.getDescription()).isEmpty();
    }

    @Test
    void testAttachmentWithLongDescription() {
        // Given
        String longDescription = "This is a very detailed description of the attachment file. " +
                                "It contains important information about the content, purpose, " +
                                "and context of this file within the project. ".repeat(10);
        attachment.setDescription(longDescription);
        
        // Then
        assertThat(attachment.getDescription()).hasSizeGreaterThan(100);
        assertThat(attachment.getDescription()).contains("important information");
    }

    @Test
    void testEqualsAndHashCodeWithStoragePath() {
        // Given
        Attachment attachment1 = new Attachment();
        attachment1.setId(1L);
        attachment1.setStoragePath("/path/to/file1.pdf");

        Attachment attachment2 = new Attachment();
        attachment2.setId(1L); // Same ID
        attachment2.setStoragePath("/path/to/file1.pdf"); // Same storagePath

        Attachment attachment3 = new Attachment();
        attachment3.setId(1L); // Same ID
        attachment3.setStoragePath("/different/path/file1.pdf"); // Different storagePath

        Attachment attachment4 = new Attachment();
        attachment4.setId(2L); // Different ID
        attachment4.setStoragePath("/path/to/file1.pdf"); // Same storagePath

        // Then: equals по id + storagePath
        assertThat(attachment1)
          .isEqualTo(attachment2); // одинаковые id и storagePath
          .isNotEqualTo(attachment3); // разные storagePath
          .isNotEqualTo(attachment4); // разные id
        assertThat(attachment1.hashCode()).isEqualTo(attachment2.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        // Given
        Attachment emptyAttachment = new Attachment();

        // Then
        assertThat(emptyAttachment.getId()).isNull();
        assertThat(emptyAttachment.getIssue()).isNull();
        assertThat(emptyAttachment.getUploader()).isNull();
        assertThat(emptyAttachment.getFileName()).isNull();
        assertThat(emptyAttachment.getFileType()).isNull();
        assertThat(emptyAttachment.getFileSizeBytes()).isNull();
        assertThat(emptyAttachment.getStoragePath()).isNull();
        assertThat(emptyAttachment.getDescription()).isNull();
        assertThat(emptyAttachment.getCreatedAt()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Instant now = Instant.now();
        Attachment attachment = new Attachment(
            1L, issue, uploader, "test.txt", fileType, 512L, 
            "/uploads/test.txt", "Test file description", now
        );

        // Then
        assertThat(attachment.getId()).isEqualTo(1L);
        assertThat(attachment.getIssue()).isEqualTo(issue);
        assertThat(attachment.getUploader()).isEqualTo(uploader);
        assertThat(attachment.getFileName()).isEqualTo("test.txt");
        assertThat(attachment.getFileType()).isEqualTo(fileType);
        assertThat(attachment.getFileSizeBytes()).isEqualTo(512L);
        assertThat(attachment.getStoragePath()).isEqualTo("/uploads/test.txt");
        assertThat(attachment.getDescription()).isEqualTo("Test file description");
        assertThat(attachment.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void testCreationTimestamp() {
        // Given
        Attachment newAttachment = new Attachment();
        
        // When
        Instant before = Instant.now();
        newAttachment.setCreatedAt(Instant.now());
        Instant after = Instant.now();

        // Then
        assertThat(newAttachment.getCreatedAt()).isBetween(before, after);
    }

    @Test
    void testAttachmentWithSpecialCharactersInDescription() {
        // Given
        attachment.setDescription("File contains: code-samples, (important) notes, & references!");
        attachment.setFileName("special-chars.pdf");
        
        // Then
        assertThat(attachment.getDescription()).contains("code-samples");
        assertThat(attachment.getDescription()).contains("(important)");
        assertThat(attachment.getDescription()).contains("& references!");
    }

    @Test
    void testDescriptionDoesNotAffectEqualsAndHashCode() {
        // Given
        Attachment attachment1 = new Attachment();
        attachment1.setId(1L);
        attachment1.setStoragePath("/same/path/file.pdf");
        attachment1.setDescription("First description");

        Attachment attachment2 = new Attachment();
        attachment2.setId(1L);
        attachment2.setStoragePath("/same/path/file.pdf");
        attachment2.setDescription("Different description"); // Разное описание

        // Then: description не влияет на equals/hashCode
        assertThat(attachment1).isEqualTo(attachment2);
        assertThat(attachment1.hashCode()).isEqualTo(attachment2.hashCode());
    }

    @Test
    void testAttachmentWithMultilineDescription() {
        // Given
        String multilineDescription = "First line of description\n" +
                                    "Second line with details\n" +
                                    "Third line with additional info";
        attachment.setDescription(multilineDescription);
        
        // Then
        assertThat(attachment.getDescription()).contains("First line");
        assertThat(attachment.getDescription()).contains("\n");
        assertThat(attachment.getDescription()).hasLineCount(3);
    }
}
