package ru.mymsoft.my_jira.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;

@DisplayName("ActionTypeDto Validation Tests")
class ActionTypeDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Nested
    @DisplayName("Valid ActionTypeDto creation")
    class ValidCreationTests {

        @Test
        @DisplayName("Should create valid ActionTypeDto with all fields")
        void testValidActionTypeDto() {
            ActionTypeDto dto = new ActionTypeDto(1L, "STATUS_CHANGE");

            assertEquals(1L, dto.id());
            assertEquals("STATUS_CHANGE", dto.name());
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "STATUS_CHANGE",
            "Задача создана",
            "Comment Added",
            "ASSIGNED",
            "Блокировано",
            "Issue-Resolved",
            "TASK_COMPLETED"
        })
        @DisplayName("Should accept valid names with different formats")
        void testValidNames(String validName) {
            ActionTypeDto dto = new ActionTypeDto(1L, validName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Name '" + validName + "' should be valid");
        }

        @Test
        @DisplayName("Should accept minimum valid name length")
        void testMinimumNameLength() {
            ActionTypeDto dto = new ActionTypeDto(1L, "OK");
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Should accept maximum valid name length")
        void testMaximumNameLength() {
            String maxLengthName = "A".repeat(100);
            ActionTypeDto dto = new ActionTypeDto(1L, maxLengthName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty());
        }
    }

    @Nested
    @DisplayName("Invalid ActionTypeDto creation")
    class InvalidCreationTests {

        @ParameterizedTest
        @NullSource
        @DisplayName("Should reject null ID")
        void testNullId(Long nullId) {
            ActionTypeDto dto = new ActionTypeDto(nullId, "STATUS_CHANGE");
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
            assertEquals("id", violations.iterator().next().getPropertyPath().toString());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Should reject null name")
        void testNullName(String nullName) {
            ActionTypeDto dto = new ActionTypeDto(1L, nullName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertFalse(violations.isEmpty());
            assertTrue(violations.stream().anyMatch(v ->
                "name".equals(v.getPropertyPath().toString()) &&
                    v.getMessage().contains("пустым")
            ));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @EmptySource
        @DisplayName("Should reject blank or empty name")
        void testBlankName(String blankName) {
            ActionTypeDto dto = new ActionTypeDto(1L, blankName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertFalse(violations.isEmpty());
            assertTrue(violations.stream().anyMatch(v ->
                "name".equals(v.getPropertyPath().toString())
            ));
        }

        @ParameterizedTest
        @ValueSource(strings = {"A"})
        @DisplayName("Should reject name shorter than 2 characters")
        void testNameTooShort(String shortName) {
            ActionTypeDto dto = new ActionTypeDto(1L, shortName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertFalse(violations.isEmpty());
            assertTrue(violations.stream().anyMatch(v ->
                "name".equals(v.getPropertyPath().toString()) &&
                    v.getMessage().contains("от 2 до 100")
            ));
        }

        @Test
        @DisplayName("Should reject name longer than 100 characters")
        void testNameTooLong() {
            String tooLongName = "A".repeat(101);
            ActionTypeDto dto = new ActionTypeDto(1L, tooLongName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertFalse(violations.isEmpty());
            assertTrue(violations.stream().anyMatch(v ->
                "name".equals(v.getPropertyPath().toString()) &&
                    v.getMessage().contains("от 2 до 100")
            ));
        }
    }

    @Nested
    @DisplayName("Record behavior tests")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Should implement equals correctly")
        void testEquals() {
            ActionTypeDto dto1 = new ActionTypeDto(1L, "STATUS_CHANGE");
            ActionTypeDto dto2 = new ActionTypeDto(1L, "STATUS_CHANGE");
            ActionTypeDto dto3 = new ActionTypeDto(2L, "STATUS_CHANGE");
            ActionTypeDto dto4 = new ActionTypeDto(1L, "ASSIGNED");

            assertEquals(dto1, dto2, "DTOs with same fields should be equal");
            assertNotEquals(dto1, dto3, "DTOs with different IDs should not be equal");
            assertNotEquals(dto1, dto4, "DTOs with different names should not be equal");
            assertNotEquals(null, dto1, "DTO should not be equal to null");
            assertNotEquals("string", dto1, "DTO should not be equal to different type");
        }

        @Test
        @DisplayName("Should implement hashCode correctly")
        void testHashCode() {
            ActionTypeDto dto1 = new ActionTypeDto(1L, "STATUS_CHANGE");
            ActionTypeDto dto2 = new ActionTypeDto(1L, "STATUS_CHANGE");
            ActionTypeDto dto3 = new ActionTypeDto(2L, "STATUS_CHANGE");

            assertEquals(dto1.hashCode(), dto2.hashCode(),
                "Equal DTOs should have same hash code");
            assertNotEquals(dto1.hashCode(), dto3.hashCode(),
                "Different DTOs should have different hash codes");
        }

        @Test
        @DisplayName("Should implement toString correctly")
        void testToString() {
            ActionTypeDto dto = new ActionTypeDto(1L, "STATUS_CHANGE");
            String toString = dto.toString();

            assertTrue(toString.contains("ActionTypeDto"));
            assertTrue(toString.contains("id=1"));
            assertTrue(toString.contains("name=STATUS_CHANGE"));
        }

        @Test
        @DisplayName("Should provide access to record components")
        void testRecordComponents() {
            ActionTypeDto dto = new ActionTypeDto(1L, "STATUS_CHANGE");

            assertEquals(1L, dto.id());
            assertEquals("STATUS_CHANGE", dto.name());
        }
    }

    @Nested
    @DisplayName("Edge cases and boundary conditions")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle special characters in name")
        void testSpecialCharactersInName() {
            String specialName = "Статус изменен! @#$%^&*()";
            ActionTypeDto dto = new ActionTypeDto(1L, specialName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Special characters should be allowed in name");
        }

        @Test
        @DisplayName("Should handle Unicode characters in name")
        void testUnicodeCharactersInName() {
            String unicodeName = "Задача решена ✅ 🎉";
            ActionTypeDto dto = new ActionTypeDto(1L, unicodeName);
            Set<ConstraintViolation<ActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Unicode characters should be allowed in name");
        }

        @Test
        @DisplayName("Should handle numeric IDs")
        void testNumericIds() {
            Long[] validIds = {0L, 1L, 999L, Long.MAX_VALUE};

            for (Long id : validIds) {
                ActionTypeDto dto = new ActionTypeDto(id, "TEST");
                assertEquals(id, dto.id());
            }
        }
    }
}