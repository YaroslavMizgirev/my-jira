package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IssueLinkTypeTest {

    private IssueLinkType issueLinkType;
    private Validator validator;

    @BeforeEach
    void setUp() {
        issueLinkType = new IssueLinkType();
        issueLinkType.setId(1L);
        issueLinkType.setName("Blocks");
        issueLinkType.setInwardName("is blocked by");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testIssueLinkTypeCreation() {
        assertNotNull(issueLinkType);
        assertThat(issueLinkType.getId()).isEqualTo(1L);
        assertThat(issueLinkType.getName()).isEqualTo("Blocks");
        assertThat(issueLinkType.getInwardName()).isEqualTo("is blocked by");
    }

    @Test
    void testSettersAndGetters() {
        // Given
        IssueLinkType newIssueLinkType = new IssueLinkType();

        // When
        newIssueLinkType.setId(2L);
        newIssueLinkType.setName("Relates to");
        newIssueLinkType.setInwardName("is related to");

        // Then
        assertThat(newIssueLinkType.getId()).isEqualTo(2L);
        assertThat(newIssueLinkType.getName()).isEqualTo("Relates to");
        assertThat(newIssueLinkType.getInwardName()).isEqualTo("is related to");
    }

    @Test
    void testNoArgsConstructor() {
        IssueLinkType emptyIssueLinkType = new IssueLinkType();

        assertNotNull(emptyIssueLinkType);
        assertNull(emptyIssueLinkType.getId());
        assertNull(emptyIssueLinkType.getName());
        assertNull(emptyIssueLinkType.getInwardName());
    }

    @Test
    void testAllArgsConstructor() {
        IssueLinkType fullIssueLinkType = new IssueLinkType(1L, "Duplicates", "is duplicated by");

        assertThat(fullIssueLinkType.getId()).isEqualTo(1L);
        assertThat(fullIssueLinkType.getName()).isEqualTo("Duplicates");
        assertThat(fullIssueLinkType.getInwardName()).isEqualTo("is duplicated by");
    }

    @Test
    void testBuilderPattern() {
        IssueLinkType builtIssueLinkType = IssueLinkType.builder()
                .id(1L)
                .name("Clones")
                .inwardName("is cloned by")
                .build();

        assertThat(builtIssueLinkType.getId()).isEqualTo(1L);
        assertThat(builtIssueLinkType.getName()).isEqualTo("Clones");
        assertThat(builtIssueLinkType.getInwardName()).isEqualTo("is cloned by");
    }

    @Test
    void testEqualsAndHashCode() {
        IssueLinkType linkType1 = new IssueLinkType(1L, "Blocks", "is blocked by");
        IssueLinkType linkType2 = new IssueLinkType(1L, "Blocks", "is blocked by");
        IssueLinkType linkType3 = new IssueLinkType(2L, "Relates to", "is related to");
        IssueLinkType linkType4 = new IssueLinkType(1L, "Different", "different inward"); // Same id, different fields

        assertThat(linkType1)
            .isEqualTo(linkType2)
            .isNotEqualTo(linkType3)
            .isNotEqualTo(linkType4)
            .isNotEqualTo(null)
            .isNotEqualTo("string")
            .hasSameHashCodeAs(linkType2);
        assertThat(linkType1.hashCode()).isNotEqualTo(linkType3.hashCode());
        assertThat(linkType1.hashCode()).isNotEqualTo(linkType4.hashCode());
    }

    @Test
    void testToString() {
        String toString = issueLinkType.toString();

        assertThat(toString)
            .contains("IssueLinkType")
            .contains("id=1")
            .contains("name=Blocks")
            .contains("inwardName=is blocked by");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Blocks", "Relates to", "Duplicates", "Clones", "Parent/Child"})
    void testValidNames(String validName) {
        IssueLinkType validIssueLinkType = IssueLinkType.builder()
                .name(validName)
                .inwardName("is related to")
                .build();

        Set<ConstraintViolation<IssueLinkType>> violations = validator.validate(validIssueLinkType);

        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidName(String invalidName) {
        IssueLinkType invalidIssueLinkType = IssueLinkType.builder()
                .name(invalidName)
                .inwardName("is related to")
                .build();

        Set<ConstraintViolation<IssueLinkType>> violations = validator.validate(invalidIssueLinkType);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("name");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidInwardName(String invalidInwardName) {
        IssueLinkType invalidIssueLinkType = IssueLinkType.builder()
                .name("Blocks")
                .inwardName(invalidInwardName)
                .build();

        Set<ConstraintViolation<IssueLinkType>> violations = validator.validate(invalidIssueLinkType);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("inwardName");
    }

    @Test
    void testNameLengthValidation() {
        String longName = "A".repeat(101);
        IssueLinkType longNameIssueLinkType = IssueLinkType.builder()
                .name(longName)
                .inwardName("is related to")
                .build();

        Set<ConstraintViolation<IssueLinkType>> violations = validator.validate(longNameIssueLinkType);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testInwardNameLengthValidation() {
        String longInwardName = "A".repeat(101);
        IssueLinkType longInwardNameIssueLinkType = IssueLinkType.builder()
                .name("Blocks")
                .inwardName(longInwardName)
                .build();

        Set<ConstraintViolation<IssueLinkType>> violations = validator.validate(longInwardNameIssueLinkType);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testValidIssueLinkTypeWithMaxLength() {
        String maxLengthName = "A".repeat(100);
        String maxLengthInwardName = "B".repeat(100);

        IssueLinkType validIssueLinkType = IssueLinkType.builder()
                .name(maxLengthName)
                .inwardName(maxLengthInwardName)
                .build();

        Set<ConstraintViolation<IssueLinkType>> violations = validator.validate(validIssueLinkType);

        assertThat(violations).isEmpty();
    }

    @Test
    void testCommonIssueLinkTypes() {
        // Test common JIRA-like link types
        IssueLinkType blocks = IssueLinkType.builder()
                .name("Blocks")
                .inwardName("is blocked by")
                .build();

        IssueLinkType relates = IssueLinkType.builder()
                .name("Relates to")
                .inwardName("is related to")
                .build();

        IssueLinkType duplicates = IssueLinkType.builder()
                .name("Duplicates")
                .inwardName("is duplicated by")
                .build();

        IssueLinkType clones = IssueLinkType.builder()
                .name("Clones")
                .inwardName("is cloned by")
                .build();

        assertThat(blocks.getName()).isEqualTo("Blocks");
        assertThat(blocks.getInwardName()).isEqualTo("is blocked by");
        assertThat(relates.getName()).isEqualTo("Relates to");
        assertThat(relates.getInwardName()).isEqualTo("is related to");
        assertThat(duplicates.getName()).isEqualTo("Duplicates");
        assertThat(duplicates.getInwardName()).isEqualTo("is duplicated by");
        assertThat(clones.getName()).isEqualTo("Clones");
        assertThat(clones.getInwardName()).isEqualTo("is cloned by");
    }

    @Test
    void testUniqueConstraintBusinessLogic() {
        // Test that name and inwardName should be unique (business logic)
        IssueLinkType type1 = IssueLinkType.builder()
                .name("Blocks")
                .inwardName("is blocked by")
                .build();

        IssueLinkType type2 = IssueLinkType.builder()
                .name("Blocks") // Same name - should be unique
                .inwardName("is blocked by")
                .build();

        IssueLinkType type3 = IssueLinkType.builder()
                .name("Different")
                .inwardName("is blocked by") // Same inward name - should be unique
                .build();

        // They are different objects
        assertThat(type1)
            .isNotSameAs(type2)
            .isNotSameAs(type3);

        // If they had same values for all fields, they would be equal
        type2.setId(1L);
        type1.setId(1L);
        assertThat(type1).isEqualTo(type2);
    }

    @Test
    void testBuilderWithPartialData() {
        // Test that builder can create objects with partial data
        IssueLinkType partialLinkType = IssueLinkType.builder()
                .name("Test Type")
                .build();

        assertThat(partialLinkType.getName()).isEqualTo("Test Type");
        assertNull(partialLinkType.getId());
        assertNull(partialLinkType.getInwardName());
    }

    @Test
    void testFieldConsistency() {
        // Test that name and inwardName are semantically different
        IssueLinkType linkType = IssueLinkType.builder()
                .name("Parent")
                .inwardName("Child")
                .build();

        // In JIRA, name is the outward description, inwardName is the reverse
        assertThat(linkType.getName()).isEqualTo("Parent");
        assertThat(linkType.getInwardName()).isEqualTo("Child");

        // They should typically be different
        assertThat(linkType.getName()).isNotEqualTo(linkType.getInwardName());
    }

    @Test
    void testCaseSensitivity() {
        // Names might be case-sensitive depending on database collation
        IssueLinkType lowerCase = IssueLinkType.builder()
                .name("blocks")
                .inwardName("is blocked by")
                .build();

        IssueLinkType upperCase = IssueLinkType.builder()
                .name("BLOCKS")
                .inwardName("IS BLOCKED BY")
                .build();

        assertThat(lowerCase.getName()).isEqualTo("blocks");
        assertThat(upperCase.getName()).isEqualTo("BLOCKS");
        assertThat(lowerCase.getName()).isNotEqualTo(upperCase.getName());
    }

    @Test
    void testSpecialCharactersInNames() {
        // Test that special characters are allowed in names
        IssueLinkType specialCharType = IssueLinkType.builder()
                .name("Parent/Child")
                .inwardName("Child/Parent")
                .build();

        Set<ConstraintViolation<IssueLinkType>> violations = validator.validate(specialCharType);

        // Should be valid as long as not null/empty and within length limits
        assertThat(violations).isEmpty();
        assertThat(specialCharType.getName()).isEqualTo("Parent/Child");
        assertThat(specialCharType.getInwardName()).isEqualTo("Child/Parent");
    }
}
