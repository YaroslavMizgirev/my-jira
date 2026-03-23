package ru.mymsoft.my_jira.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import ru.mymsoft.my_jira.dto.ActionTypeDto;
import ru.mymsoft.my_jira.dto.CreateActionTypeDto;
import ru.mymsoft.my_jira.exception.DuplicateActionTypeNameException;
import ru.mymsoft.my_jira.model.ActionType;
import ru.mymsoft.my_jira.repository.ActionTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActionTypeService Tests")
class ActionTypeServiceTest {

    @Mock
    private ActionTypeRepository actionTypeRepository;

    @InjectMocks
    private ActionTypeService actionTypeService;

    private ActionType testActionType;
    private CreateActionTypeDto createDto;
    private ActionTypeDto updateDto;

    @BeforeEach
    void setUp() {
        testActionType = ActionType.builder()
                .id(1L)
                .name("Test Action")
                .build();

        createDto = new CreateActionTypeDto("New Action");
        updateDto = new ActionTypeDto(1L, "Updated Action");
    }

    @Test
    @DisplayName("Should create action type successfully when name is unique")
    void createActionType_Success_WhenNameIsUnique() {
        // Given
        when(actionTypeRepository.existsByName(createDto.name())).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(testActionType);

        // When
        ActionTypeDto result = actionTypeService.createActionType(createDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Action");
        verify(actionTypeRepository).existsByName(createDto.name());
        verify(actionTypeRepository).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should throw DuplicateActionTypeNameException when creating action type with existing name")
    void createActionType_ThrowsException_WhenNameExists() {
        // Given
        when(actionTypeRepository.existsByName(createDto.name())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> actionTypeService.createActionType(createDto))
                .isInstanceOf(DuplicateActionTypeNameException.class)
                .hasMessageContaining("ActionType with name 'New Action' already exists");

        verify(actionTypeRepository).existsByName(createDto.name());
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should get action type by ID successfully")
    void getActionTypeById_Success() {
        // Given
        when(actionTypeRepository.findById(1L)).thenReturn(Optional.of(testActionType));

        // When
        ActionTypeDto result = actionTypeService.getActionTypeById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Action");
        verify(actionTypeRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when getting action type by non-existent ID")
    void getActionTypeById_ThrowsException_WhenNotFound() {
        // Given
        when(actionTypeRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> actionTypeService.getActionTypeById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ActionType with ID: 999 - not found");

        verify(actionTypeRepository).findById(999L);
    }

    @Test
    @DisplayName("Should get action type by name successfully")
    void getActionTypeByName_Success() {
        // Given
        when(actionTypeRepository.findByName("Test Action")).thenReturn(Optional.of(testActionType));

        // When
        ActionTypeDto result = actionTypeService.getActionTypeByName("Test Action");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Action");
        verify(actionTypeRepository).findByName("Test Action");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when getting action type by non-existent name")
    void getActionTypeByName_ThrowsException_WhenNotFound() {
        // Given
        when(actionTypeRepository.findByName("Non-existent")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> actionTypeService.getActionTypeByName("Non-existent"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ActionType with name: Non-existent - not found");

        verify(actionTypeRepository).findByName("Non-existent");
    }

    @Test
    @DisplayName("Should list action types with name filter successfully")
    void listActionTypes_WithFilter_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<ActionType> actionTypes = List.of(testActionType);
        Page<ActionType> actionTypePage = new PageImpl<>(actionTypes, pageable, 1);

        when(actionTypeRepository.findAllByNameContainingIgnoreCase("test", pageable))
                .thenReturn(actionTypePage);

        // When
        Page<ActionTypeDto> result = actionTypeService.listActionTypes("test", pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Test Action");
        verify(actionTypeRepository).findAllByNameContainingIgnoreCase("test", pageable);
    }

    @Test
    @DisplayName("Should list all action types when filter is null")
    void listActionTypes_WithNullFilter_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<ActionType> actionTypes = List.of(testActionType);
        Page<ActionType> actionTypePage = new PageImpl<>(actionTypes, pageable, 1);

        when(actionTypeRepository.findAllByNameContainingIgnoreCase("", pageable))
                .thenReturn(actionTypePage);

        // When
        Page<ActionTypeDto> result = actionTypeService.listActionTypes(null, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(actionTypeRepository).findAllByNameContainingIgnoreCase("", pageable);
    }

    @Test
    @DisplayName("Should list all action types sorted ascending")
    void listAllActionTypesSorted_Ascending_Success() {
        // Given
        ActionType actionType2 = ActionType.builder().id(2L).name("Another Action").build();
        List<ActionType> actionTypes = List.of(actionType2, testActionType);

        when(actionTypeRepository.findAllByOrderByNameAsc()).thenReturn(actionTypes);

        // When
        List<ActionTypeDto> result = actionTypeService.listAllActionTypesSorted(true);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Another Action");
        assertThat(result.get(1).name()).isEqualTo("Test Action");
        verify(actionTypeRepository).findAllByOrderByNameAsc();
        verify(actionTypeRepository, never()).findAllByOrderByNameDesc();
    }

    @Test
    @DisplayName("Should list all action types sorted descending")
    void listAllActionTypesSorted_Descending_Success() {
        // Given
        ActionType actionType2 = ActionType.builder().id(2L).name("Another Action").build();
        List<ActionType> actionTypes = List.of(testActionType, actionType2);

        when(actionTypeRepository.findAllByOrderByNameDesc()).thenReturn(actionTypes);

        // When
        List<ActionTypeDto> result = actionTypeService.listAllActionTypesSorted(false);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Test Action");
        assertThat(result.get(1).name()).isEqualTo("Another Action");
        verify(actionTypeRepository).findAllByOrderByNameDesc();
        verify(actionTypeRepository, never()).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update action type successfully")
    void updateActionType_Success() {
        // Given
        when(actionTypeRepository.findById(1L)).thenReturn(Optional.of(testActionType));
        when(actionTypeRepository.existsByName("Updated Action")).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(testActionType);

        // When
        ActionTypeDto result = actionTypeService.updateActionType(1L, updateDto);

        // Then
        assertThat(result).isNotNull();
        verify(actionTypeRepository).findById(1L);
        verify(actionTypeRepository).existsByName("Updated Action");
        verify(actionTypeRepository).save(testActionType);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating with mismatched IDs")
    void updateActionType_ThrowsException_WhenIdsMismatch() {
        // Given
        ActionTypeDto mismatchedDto = new ActionTypeDto(2L, "Updated Action");

        // When & Then
        assertThatThrownBy(() -> actionTypeService.updateActionType(1L, mismatchedDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ID in path and body must match");

        verify(actionTypeRepository, never()).findById(anyLong());
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating non-existent action type")
    void updateActionType_ThrowsException_WhenNotFound() {
        // Given
        ActionTypeDto updateDto = new ActionTypeDto(999L, "Updated Action");

        when(actionTypeRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> actionTypeService.updateActionType(999L, updateDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ActionType with ID: 999 - not found");

        verify(actionTypeRepository).findById(999L);
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should throw DuplicateActionTypeNameException when updating to existing name")
    void updateActionType_ThrowsException_WhenNameExists() {
        // Given
        ActionType existingActionType = ActionType.builder()
                .id(1L)
                .name("Original Name")
                .build();

        when(actionTypeRepository.findById(1L)).thenReturn(Optional.of(existingActionType));
        when(actionTypeRepository.existsByName("Updated Action")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> actionTypeService.updateActionType(1L, updateDto))
                .isInstanceOf(DuplicateActionTypeNameException.class)
                .hasMessageContaining("ActionType with name 'Updated Action' already exists");

        verify(actionTypeRepository).findById(1L);
        verify(actionTypeRepository).existsByName("Updated Action");
        verify(actionTypeRepository, never()).save(any(ActionType.class));
    }

    @Test
    @DisplayName("Should update action type successfully when name is the same")
    void updateActionType_Success_WhenNameIsSame() {
        // Given
        ActionTypeDto sameNameDto = new ActionTypeDto(1L, "Test Action");
        when(actionTypeRepository.findById(1L)).thenReturn(Optional.of(testActionType));
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(testActionType);

        // When
        ActionTypeDto result = actionTypeService.updateActionType(1L, sameNameDto);

        // Then
        assertThat(result).isNotNull();
        verify(actionTypeRepository).findById(1L);
        verify(actionTypeRepository, never()).existsByName(anyString());
        verify(actionTypeRepository).save(testActionType);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    @DisplayName("Should not update name when it's null, empty or blank")
    void updateActionType_DoesNotUpdateName_WhenNameIsInvalid(String invalidName) {
        // Given
        ActionTypeDto invalidNameDto = new ActionTypeDto(1L, invalidName);
        when(actionTypeRepository.findById(1L)).thenReturn(Optional.of(testActionType));
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(testActionType);

        // When
        ActionTypeDto result = actionTypeService.updateActionType(1L, invalidNameDto);

        // Then
        assertThat(result).isNotNull();
        verify(actionTypeRepository).findById(1L);
        verify(actionTypeRepository, never()).existsByName(anyString());
        verify(actionTypeRepository).save(testActionType);
    }

    @Test
    @DisplayName("Should delete action type successfully")
    void deleteActionType_Success() {
        // Given
        when(actionTypeRepository.existsById(1L)).thenReturn(true);

        // When
        actionTypeService.deleteActionType(1L);

        // Then
        verify(actionTypeRepository).existsById(1L);
        verify(actionTypeRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when deleting non-existent action type")
    void deleteActionType_ThrowsException_WhenNotFound() {
        // Given
        when(actionTypeRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> actionTypeService.deleteActionType(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ActionType with ID: 999 - not found");

        verify(actionTypeRepository).existsById(999L);
        verify(actionTypeRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should convert ActionType to ActionTypeDto correctly")
    void toDto_Success() {
        // When
        ActionTypeDto result = (ActionTypeDto) ReflectionTestUtils.invokeMethod(
                actionTypeService, "toDto", testActionType);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Action");
    }

    @ParameterizedTest
    @MethodSource("provideValidActionTypeNames")
    @DisplayName("Should create action type with various valid names")
    void createActionType_WithValidNames_Success(String name) {
        // Given
        CreateActionTypeDto validDto = new CreateActionTypeDto(name);
        ActionType validActionType = ActionType.builder().id(1L).name(name).build();

        when(actionTypeRepository.existsByName(name)).thenReturn(false);
        when(actionTypeRepository.save(any(ActionType.class))).thenReturn(validActionType);

        // When
        ActionTypeDto result = actionTypeService.createActionType(validDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(name);
        verify(actionTypeRepository).existsByName(name);
        verify(actionTypeRepository).save(any(ActionType.class));
    }

    private static Stream<Arguments> provideValidActionTypeNames() {
        return Stream.of(
                Arguments.of("Simple Name"),
                Arguments.of("Name With Spaces"),
                Arguments.of("Name-With-Dashes"),
                Arguments.of("Name_With_Underscores"),
                Arguments.of("NAME123"),
                Arguments.of("Name with numbers 123"),
                Arguments.of("A"),
                Arguments.of("Valid long name")
        );
    }
}
