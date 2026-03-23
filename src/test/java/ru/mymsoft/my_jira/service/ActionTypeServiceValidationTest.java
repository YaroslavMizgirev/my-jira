package ru.mymsoft.my_jira.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ru.mymsoft.my_jira.dto.ActionTypeDto;
import ru.mymsoft.my_jira.dto.CreateActionTypeDto;
import ru.mymsoft.my_jira.exception.DuplicateActionTypeNameException;
import ru.mymsoft.my_jira.model.ActionType;
import ru.mymsoft.my_jira.repository.ActionTypeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActionTypeService Validation Tests")
class ActionTypeServiceValidationTest {

    @Mock
    private ActionTypeRepository actionTypeRepository;

    @InjectMocks
    private ActionTypeService actionTypeService;

    private ActionType testActionType;

    @BeforeEach
    void setUp() {
        testActionType = ActionType.builder()
                .id(1L)
                .name("Test Action")
                .build();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Should handle invalid names in create operation")
    void createActionType_WithInvalidName_ThrowsException(String invalidName) {
        // Given
        CreateActionTypeDto createDto = new CreateActionTypeDto(invalidName);

        // When & Then
        assertThatThrownBy(() -> actionTypeService.createActionType(createDto))
                .isInstanceOf(IllegalArgumentException.class);

        verify(actionTypeRepository, never()).existsByName(anyString());
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Should handle invalid names in get by name operation")
    void getActionTypeByName_WithInvalidName_ThrowsException(String invalidName) {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.getActionTypeByName(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name cannot be null or empty");

        verify(actionTypeRepository, never()).findByName(anyString());
    }

    @Test
    @DisplayName("Should throw exception for null ID in get by ID operation")
    void getActionTypeById_WithNullId_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.getActionTypeById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID cannot be null");

        verify(actionTypeRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception for negative ID in get by ID operation")
    void getActionTypeById_WithNegativeId_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.getActionTypeById(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID must be positive");

        verify(actionTypeRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception for zero ID in get by ID operation")
    void getActionTypeById_WithZeroId_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.getActionTypeById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID must be positive");

        verify(actionTypeRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception for null ID in delete operation")
    void deleteActionType_WithNullId_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.deleteActionType(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID cannot be null");

        verify(actionTypeRepository, never()).existsById(anyLong());
        verify(actionTypeRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception for negative ID in delete operation")
    void deleteActionType_WithNegativeId_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.deleteActionType(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ID must be positive");

        verify(actionTypeRepository, never()).existsById(anyLong());
        verify(actionTypeRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should handle null pageable in list operation")
    void listActionTypes_WithNullPageable_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.listActionTypes("test", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Pageable cannot be null");

        verify(actionTypeRepository, never()).findAllByNameContainingIgnoreCase(anyString(), any());
    }

    @Test
    @DisplayName("Should handle very long names in create operation")
    void createActionType_WithVeryLongName_ThrowsException() {
        // Given
        String veryLongName = "a".repeat(101);
        CreateActionTypeDto createDto = new CreateActionTypeDto(veryLongName);

        // When & Then
        assertThatThrownBy(() -> actionTypeService.createActionType(createDto))
                .isInstanceOf(IllegalArgumentException.class);

        verify(actionTypeRepository, never()).existsByName(anyString());
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should handle names with special characters")
    void createActionType_WithSpecialCharacters_Success() {
        // Given
        String specialName = "Action!@#$%^&*()_+-=[]{}|;':\",./<>?";
        CreateActionTypeDto createDto = new CreateActionTypeDto(specialName);
        ActionType specialActionType = ActionType.builder().id(1L).name(specialName).build();

        when(actionTypeRepository.existsByName(specialName)).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(specialActionType);

        // When
        ActionTypeDto result = actionTypeService.createActionType(createDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(specialName);
        verify(actionTypeRepository).existsByName(specialName);
        verify(actionTypeRepository).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should handle Unicode characters in names")
    void createActionType_WithUnicodeCharacters_Success() {
        // Given
        String unicodeName = "Действие_на_русском_ñáéíóú";
        CreateActionTypeDto createDto = new CreateActionTypeDto(unicodeName);
        ActionType unicodeActionType = ActionType.builder().id(1L).name(unicodeName).build();

        when(actionTypeRepository.existsByName(unicodeName)).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(unicodeActionType);

        // When
        ActionTypeDto result = actionTypeService.createActionType(createDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(unicodeName);
        verify(actionTypeRepository).existsByName(unicodeName);
        verify(actionTypeRepository).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should handle case sensitivity in duplicate check")
    void createActionType_CaseSensitiveDuplicateCheck() {
        // Given
        CreateActionTypeDto createDto = new CreateActionTypeDto("test action");
        
        when(actionTypeRepository.existsByName("test action")).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(testActionType);

        // When
        ActionTypeDto result = actionTypeService.createActionType(createDto);

        // Then
        assertThat(result).isNotNull();
        verify(actionTypeRepository).existsByName("test action");
        verify(actionTypeRepository).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should handle update with same name but different case")
    void updateActionType_SameNameDifferentCase_Success() {
        // Given
        ActionType existingActionType = ActionType.builder()
                .id(1L)
                .name("Test Action")
                .build();
        ActionTypeDto updateDto = new ActionTypeDto(1L, "test action");

        when(actionTypeRepository.findById(1L)).thenReturn(Optional.of(existingActionType));
        when(actionTypeRepository.existsByName("test action")).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(existingActionType);

        // When
        ActionTypeDto result = actionTypeService.updateActionType(1L, updateDto);

        // Then
        assertThat(result).isNotNull();
        verify(actionTypeRepository).findById(1L);
        verify(actionTypeRepository).existsByName("test action");
        verify(actionTypeRepository).save(existingActionType);
    }

    @Test
    @DisplayName("Should handle empty list result in list all operation")
    void listAllActionTypesSorted_EmptyList_Success() {
        // Given
        when(actionTypeRepository.findAllByOrderByNameAsc()).thenReturn(List.of());

        // When
        List<ActionTypeDto> result = actionTypeService.listAllActionTypesSorted(true);

        // Then
        assertThat(result).isEmpty();
        verify(actionTypeRepository).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should handle pagination with size zero")
    void listActionTypes_WithZeroPageSize_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.listActionTypes("test", PageRequest.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Page size must not be less than one");
    }

    @Test
    @DisplayName("Should handle negative page number")
    void listActionTypes_WithNegativePageNumber_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.listActionTypes("test", PageRequest.of(-1, 10)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Page index must not be less than zero");
    }

    @Test
    @DisplayName("Should handle update with null DTO")
    void updateActionType_WithNullDto_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.updateActionType(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ActionTypeDto cannot be null");

        verify(actionTypeRepository, never()).findById(anyLong());
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should handle create with null DTO")
    void createActionType_WithNullDto_ThrowsException() {
        // When & Then
        assertThatThrownBy(() -> actionTypeService.createActionType(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CreateActionTypeDto cannot be null");

        verify(actionTypeRepository, never()).existsByName(anyString());
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid", "Valid Name", "Name With Spaces", "Name-With-Dashes", "Name_With_Underscores"})
    @DisplayName("Should accept various valid name formats")
    void validNameFormats_Accepted(String validName) {
        // Given
        CreateActionTypeDto createDto = new CreateActionTypeDto(validName);
        ActionType validActionType = ActionType.builder().id(1L).name(validName).build();

        when(actionTypeRepository.existsByName(validName)).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(validActionType);

        // When
        ActionTypeDto result = actionTypeService.createActionType(createDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(validName);
        verify(actionTypeRepository).existsByName(validName);
        verify(actionTypeRepository).save(any(ActionType.class));
    }
}
