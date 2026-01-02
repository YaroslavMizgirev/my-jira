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

class GroupTest {

    private Group group;
    private Validator validator;

    @BeforeEach
    void setUp() {
        group = new Group();
        group.setId(1L);
        group.setName("Developers");
        group.setDescription("Software development team");
        group.setSystemGroup(false);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGroupCreation() {
        assertNotNull(group);
        assertThat(group.getId()).isEqualTo(1L);
        assertThat(group.getName()).isEqualTo("Developers");
        assertThat(group.getDescription()).isEqualTo("Software development team");
        assertThat(group.isSystemGroup()).isFalse();
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Group newGroup = new Group();

        // When
        newGroup.setId(2L);
        newGroup.setName("Administrators");
        newGroup.setDescription("System administrators");
        newGroup.setSystemGroup(true);

        // Then
        assertThat(newGroup.getId()).isEqualTo(2L);
        assertThat(newGroup.getName()).isEqualTo("Administrators");
        assertThat(newGroup.getDescription()).isEqualTo("System administrators");
        assertThat(newGroup.isSystemGroup()).isTrue();
    }

    @Test
    void testNoArgsConstructor() {
        Group emptyGroup = new Group();

        assertNotNull(emptyGroup);
        assertNull(emptyGroup.getId());
        assertNull(emptyGroup.getName());
        assertNull(emptyGroup.getDescription());
        assertThat(emptyGroup.isSystemGroup()).isFalse(); // default value
    }

    @Test
    void testAllArgsConstructor() {
        Group fullGroup = new Group(1L, "Testers", "QA team", true);

        assertThat(fullGroup.getId()).isEqualTo(1L);
        assertThat(fullGroup.getName()).isEqualTo("Testers");
        assertThat(fullGroup.getDescription()).isEqualTo("QA team");
        assertThat(fullGroup.isSystemGroup()).isTrue();
    }

    @Test
    void testDefaultIsSystemGroupValue() {
        Group groupWithDefaults = new Group();

        assertThat(groupWithDefaults.isSystemGroup()).isFalse();

        // Explicitly set to null should still return default
        groupWithDefaults.setSystemGroup(null);
        assertThat(groupWithDefaults.isSystemGroup()).isNull();
    }

    @Test
    void testEqualsAndHashCode() {
        Group group1 = new Group(1L, "Developers", "Dev team", false);
        Group group2 = new Group(1L, "Developers", "Dev team", false);
        Group group3 = new Group(2L, "Admins", "Admin team", true);
        Group group4 = new Group(1L, "Testers", "Dev team", false); // Different name
        Group group5 = new Group(1L, "Developers", "Different desc", false); // Different description
        Group group6 = new Group(1L, "Developers", "Dev team", true); // Different isSystemGroup

        // Test equals
        assertThat(group1)
          .isEqualTo(group2)
          .isNotEqualTo(group3)
          .isNotEqualTo(group4)
          .isEqualTo(group5) // Description not included in equals
          .isNotEqualTo(group6) // isSystemGroup included in equals
          .isNotEqualTo(null)
          .isNotEqualTo("string")
          .hasSameHashCodeAs(group2)
          .hasSameHashCodeAs(group5); // Description not included in hashCode
        assertThat(group1.hashCode()).isNotEqualTo(group3.hashCode());
        assertThat(group1.hashCode()).isNotEqualTo(group4.hashCode());
        assertThat(group1.hashCode()).isNotEqualTo(group6.hashCode()); // isSystemGroup included in hashCode
    }

    @Test
    void testToString() {
        String toString = group.toString();

        assertThat(toString)
          .contains("Group")
          .contains("id=1")
          .contains("name=Developers")
          .contains("isSystemGroup=false");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Valid Name", "Group-1", "Team_Alpha", "123"})
    void testValidGroupNames(String validName) {
        Group validGroup = new Group(null, validName, "Description", false);

        Set<ConstraintViolation<Group>> violations = validator.validate(validGroup);

        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidGroupName(String invalidName) {
        Group invalidGroup = new Group(null, invalidName, "Description", false);

        Set<ConstraintViolation<Group>> violations = validator.validate(invalidGroup);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("must not be");
    }

    @Test
    void testNameLengthValidation() {
        String longName = "A".repeat(101);
        Group longNameGroup = new Group(null, longName, "Description", false);

        Set<ConstraintViolation<Group>> violations = validator.validate(longNameGroup);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void testValidDescription() {
        Group groupWithLongDescription = new Group();
        groupWithLongDescription.setName("Test Group");
        groupWithLongDescription.setDescription("This is a very long description that can contain many characters since it's a LOB field");

        Set<ConstraintViolation<Group>> violations = validator.validate(groupWithLongDescription);

        assertThat(violations).isEmpty();
    }

    @Test
    void testNullIsSystemGroup() {
        Group groupWithNullSystemFlag = new Group();
        groupWithNullSystemFlag.setName("Test Group");
        groupWithNullSystemFlag.setSystemGroup(null);

        Set<ConstraintViolation<Group>> violations = validator.validate(groupWithNullSystemFlag);

        // Should have violation for null isSystemGroup
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testLobDescriptionHandling() {
        String veryLongDescription = "A".repeat(10000);
        Group groupWithVeryLongDescription = new Group(null, "TestGroup", veryLongDescription, false);

        // This should not violate validation constraints
        Set<ConstraintViolation<Group>> violations = validator.validate(groupWithVeryLongDescription);

        assertThat(violations).isEmpty();
    }
}
