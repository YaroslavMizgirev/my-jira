package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileTypeUnitTest {

    private FileType fileType;
    private Validator validator;

    @BeforeEach
    void setUp() {
        fileType = new FileType();
        fileType.setId(1L);
        fileType.setExtension("pdf");
        fileType.setMimeType("application/pdf");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testFileTypeCreation() {
        assertNotNull(fileType);
        assertThat(fileType.getId()).isEqualTo(1L);
        assertThat(fileType.getExtension()).isEqualTo("pdf");
        assertThat(fileType.getMimeType()).isEqualTo("application/pdf");
    }

    @Test
    void testSettersAndGetters() {
        // Given
        FileType newFileType = new FileType();

        // When
        newFileType.setId(2L);
        newFileType.setExtension("jpg");
        newFileType.setMimeType("image/jpeg");

        // Then
        assertThat(newFileType.getId()).isEqualTo(2L);
        assertThat(newFileType.getExtension()).isEqualTo("jpg");
        assertThat(newFileType.getMimeType()).isEqualTo("image/jpeg");
    }

    @Test
    void testNoArgsConstructor() {
        FileType emptyFileType = new FileType();

        assertNotNull(emptyFileType);
        assertNull(emptyFileType.getId());
        assertNull(emptyFileType.getExtension());
        assertNull(emptyFileType.getMimeType());
    }

    @Test
    void testAllArgsConstructor() {
        FileType fullFileType = new FileType(1L, "png", "image/png");

        assertThat(fullFileType.getId()).isEqualTo(1L);
        assertThat(fullFileType.getExtension()).isEqualTo("png");
        assertThat(fullFileType.getMimeType()).isEqualTo("image/png");
    }

    @Test
    void testEqualsAndHashCode() {
        FileType fileType1 = new FileType(1L, "pdf", "application/pdf");
        FileType fileType2 = new FileType(1L, "pdf", "application/pdf");
        FileType fileType3 = new FileType(2L, "jpg", "image/jpeg");
        FileType fileType4 = new FileType(1L, "doc", "application/msword");

        assertThat(fileType1)
          .isEqualTo(fileType2)
          .isNotEqualTo(fileType3)
          .isNotEqualTo(fileType4)
          .isNotEqualTo(null);
          .isNotEqualTo("string");
          .hasSameHashCodeAs(fileType2);
        assertThat(fileType1.hashCode()).isNotEqualTo(fileType3.hashCode());
        assertThat(fileType1.hashCode()).isNotEqualTo(fileType4.hashCode());
    }

    @Test
    void testToString() {
        String toString = fileType.toString();

        assertThat(toString).contains("FileType");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("extension=pdf");
        assertThat(toString).contains("mimeType=application/pdf");
    }

    @ParameterizedTest
    @CsvSource({
        "pdf, application/pdf",
        "jpg, image/jpeg",
        "png, image/png",
        "doc, application/msword",
        "txt, text/plain"
    })
    void testValidFileTypes(String extension, String mimeType) {
        FileType validFileType = new FileType(null, extension, mimeType);
        Set<ConstraintViolation<FileType>> violations = validator.validate(validFileType);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidExtension(String invalidExtension) {
        FileType invalidFileType = new FileType(null, invalidExtension, "application/pdf");
        Set<ConstraintViolation<FileType>> violations = validator.validate(invalidFileType);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must not be");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidMimeType(String invalidMimeType) {
        FileType invalidFileType = new FileType(null, "pdf", invalidMimeType);
        Set<ConstraintViolation<FileType>> violations = validator.validate(invalidFileType);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must not be");
    }

    @Test
    void testExtensionLengthValidation() {
        FileType longExtension = new FileType(null, "thisiswaytoolongextension", "application/pdf");
        Set<ConstraintViolation<FileType>> violations = validator.validate(longExtension);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testMimeTypeLengthValidation() {
        String longMimeType = "a".repeat(101);
        FileType longMimeTypeFile = new FileType(null, "pdf", longMimeType);
        Set<ConstraintViolation<FileType>> violations = validator.validate(longMimeTypeFile);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testBuilderPattern() {
        FileType builtFileType = FileType.builder()
            .id(1L)
            .extension("pdf")
            .mimeType("application/pdf")
            .build();

        assertThat(builtFileType.getId()).isEqualTo(1L);
        assertThat(builtFileType.getExtension()).isEqualTo("pdf");
        assertThat(builtFileType.getMimeType()).isEqualTo("application/pdf");
    }
}
