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

@DisplayName("CreateActionTypeDto Validation Tests")
class CreateActionTypeDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Nested
    @DisplayName("Valid CreateActionTypeDto creation")
    class ValidCreationTests {

        @Test
        @DisplayName("Should create valid CreateActionTypeDto")
        void testValidCreateActionTypeDto() {
            CreateActionTypeDto dto = new CreateActionTypeDto("STATUS_CHANGE");

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
            "TASK_COMPLETED",
            "User Assigned",
            "Priority Changed",
            "Due Date Set"
        })
        @DisplayName("Should accept valid action type names")
        void testValidActionTypeNames(String validName) {
            CreateActionTypeDto dto = new CreateActionTypeDto(validName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Action type name '" + validName + "' should be valid");
        }

        @Test
        @DisplayName("Should accept minimum valid name length")
        void testMinimumNameLength() {
            CreateActionTypeDto dto = new CreateActionTypeDto("OK");
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("Should accept maximum valid name length")
        void testMaximumNameLength() {
            String maxLengthName = "A".repeat(100);
            CreateActionTypeDto dto = new CreateActionTypeDto(maxLengthName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty());
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "STATUS_CHANGE",
            "STATUS-CHANGE",
            "STATUS_CHANGE",
            "STATUS CHANGE",
            "STATUS.CHANGE",
            "STATUS_CHANGE_123",
            "123_STATUS_CHANGE"
        })
        @DisplayName("Should accept various name formats")
        void testVariousNameFormats(String validFormat) {
            CreateActionTypeDto dto = new CreateActionTypeDto(validFormat);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Name format '" + validFormat + "' should be valid");
        }
    }

    @Nested
    @DisplayName("Invalid CreateActionTypeDto creation")
    class InvalidCreationTests {

        @ParameterizedTest
        @NullSource
        @DisplayName("Should reject null name")
        void testNullName(String nullName) {
            CreateActionTypeDto dto = new CreateActionTypeDto(nullName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertFalse(violations.isEmpty());
            assertEquals(2, violations.size()); // Both @NotNull and @NotBlank should trigger

            boolean hasNotNull = violations.stream().anyMatch(v ->
                "name".equals(v.getPropertyPath().toString()) &&
                    v.getMessage().contains("NULL")
            );
            boolean hasNotBlank = violations.stream().anyMatch(v ->
                "name".equals(v.getPropertyPath().toString()) &&
                    v.getMessage().contains("пустым")
            );

            assertTrue(hasNotNull, "Should have @NotNull violation");
            assertTrue(hasNotBlank, "Should have @NotBlank violation");
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "  \t  \n  "})
        @EmptySource
        @DisplayName("Should reject blank or empty name")
        void testBlankName(String blankName) {
            CreateActionTypeDto dto = new CreateActionTypeDto(blankName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertFalse(violations.isEmpty());
            assertTrue(violations.stream().anyMatch(v ->
                "name".equals(v.getPropertyPath().toString()) &&
                    v.getMessage().contains("пустым")
            ));
        }

        @ParameterizedTest
        @ValueSource(strings = {"A"})
        @DisplayName("Should reject name shorter than 2 characters")
        void testNameTooShort(String shortName) {
            CreateActionTypeDto dto = new CreateActionTypeDto(shortName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

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
            CreateActionTypeDto dto = new CreateActionTypeDto(tooLongName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

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
            CreateActionTypeDto dto1 = new CreateActionTypeDto("STATUS_CHANGE");
            CreateActionTypeDto dto2 = new CreateActionTypeDto("STATUS_CHANGE");
            CreateActionTypeDto dto3 = new CreateActionTypeDto("ASSIGNED");

            assertEquals(dto1, dto2, "DTOs with same name should be equal");
            assertNotEquals(dto1, dto3, "DTOs with different names should not be equal");
            assertNotEquals(null, dto1, "DTO should not be equal to null");
            assertNotEquals("string", dto1, "DTO should not be equal to different type");
        }

        @Test
        @DisplayName("Should implement hashCode correctly")
        void testHashCode() {
            CreateActionTypeDto dto1 = new CreateActionTypeDto("STATUS_CHANGE");
            CreateActionTypeDto dto2 = new CreateActionTypeDto("STATUS_CHANGE");
            CreateActionTypeDto dto3 = new CreateActionTypeDto("ASSIGNED");

            assertEquals(dto1.hashCode(), dto2.hashCode(),
                "Equal DTOs should have same hash code");
            assertNotEquals(dto1.hashCode(), dto3.hashCode(),
                "Different DTOs should have different hash codes");
        }

        @Test
        @DisplayName("Should implement toString correctly")
        void testToString() {
            CreateActionTypeDto dto = new CreateActionTypeDto("STATUS_CHANGE");
            String toString = dto.toString();

            assertTrue(toString.contains("CreateActionTypeDto"));
            assertTrue(toString.contains("name=STATUS_CHANGE"));
        }

        @Test
        @DisplayName("Should provide access to record components")
        void testRecordComponents() {
            CreateActionTypeDto dto = new CreateActionTypeDto("STATUS_CHANGE");

            assertEquals("STATUS_CHANGE", dto.name());
        }
    }

    @Nested
    @DisplayName("Business logic validation tests")
    class BusinessLogicTests {

        @ParameterizedTest
        @ValueSource(strings = {
            "status_change",
            "STATUS_CHANGE",
            "Status_Change",
            "STATUS CHANGE",
            "Статус изменен",
            "статус изменен"
        })
        @DisplayName("Should accept various case formats")
        void testCaseSensitivity(String name) {
            CreateActionTypeDto dto = new CreateActionTypeDto(name);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Name '" + name + "' should be valid regardless of case");
        }

        @Test
        @DisplayName("Should handle common action type names")
        void testCommonActionTypeNames() {
            String[] commonNames = {
                "Задача создана",
                "Задача решена",
                "Задача закрыта",
                "Комментарий добавлен",
                "Исполнитель назначен",
                "Приоритет изменен",
                "Описание обновлено",
                "Вложение добавлено",
                "Срок установлен",
                "Статус задачи изменился"
            };

            for (String name : commonNames) {
                CreateActionTypeDto dto = new CreateActionTypeDto(name);
                Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

                assertTrue(violations.isEmpty(),
                    "Common action type name '" + name + "' should be valid");
            }
        }
    }

    @Nested
    @DisplayName("Edge cases and boundary conditions")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle special characters in name")
        void testSpecialCharactersInName() {
            String specialName = "Статус изменен! @#$%^&*()";
            CreateActionTypeDto dto = new CreateActionTypeDto(specialName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Special characters should be allowed in name");
        }

        @Test
        @DisplayName("Should handle Unicode characters in name")
        void testUnicodeCharactersInName() {
            String unicodeName = "Задача решена ✅ 🎉 📋";
            CreateActionTypeDto dto = new CreateActionTypeDto(unicodeName);
            Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

            assertTrue(violations.isEmpty(),
                "Unicode characters should be allowed in name");
        }

        @Test
        @DisplayName("Should handle names with numbers")
        void testNamesWithNumbers() {
            String[] namesWithNumbers = {
                "Status 1",
                "Phase 2 Complete",
                "2023 Task Created",
                "Level 5 Priority"
            };

            for (String name : namesWithNumbers) {
                CreateActionTypeDto dto = new CreateActionTypeDto(name);
                Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

                assertTrue(violations.isEmpty(),
                    "Name with numbers '" + name + "' should be valid");
            }
        }

        @Test
        @DisplayName("Should handle boundary whitespace")
        void testBoundaryWhitespace() {
            String[] validNames = {
                "  Valid Name  ",
                "\tValid Name\t",
                "\nValid Name\n"
            };

            for (String name : validNames) {
                CreateActionTypeDto dto = new CreateActionTypeDto(name);
                Set<ConstraintViolation<CreateActionTypeDto>> violations = validator.validate(dto);

                assertTrue(violations.isEmpty(),
                    "Name with boundary whitespace '" + name + "' should be valid");
            }
        }
    }

    @Nested
    @DisplayName("Performance and stress tests")
    class PerformanceTests {

        @Test
        @DisplayName("Should handle validation efficiently")
        void testValidationPerformance() {
            String validName = "Valid Action Type Name";

            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                CreateActionTypeDto dto = new CreateActionTypeDto(validName);
                validator.validate(dto);
            }
            long endTime = System.currentTimeMillis();

            assertTrue((endTime - startTime) < 1000,
                "Validation should complete quickly even for 1000 iterations");
        }

        @Test
        @DisplayName("Should handle many violations efficiently")
        void testManyViolationsPerformance() {
            String invalidName = "A".repeat(200); // Too long

            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                CreateActionTypeDto dto = new CreateActionTypeDto(invalidName);
                validator.validate(dto);
            }
            long endTime = System.currentTimeMillis();

            assertTrue((endTime - startTime) < 1000,
                "Violation detection should complete quickly");
        }
    }
}
