package ru.mymsoft.my_jira.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ru.mymsoft.my_jira.dto.ActionTypeDto;
import ru.mymsoft.my_jira.dto.CreateActionTypeDto;
import ru.mymsoft.my_jira.exception.DuplicateActionTypeNameException;
import ru.mymsoft.my_jira.service.ActionTypeService;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

/**
 * Unit тесты для ActionTypeController.
 * Тестируют логику контроллера с моками сервиса.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ActionTypeController Unit Tests")
class ActionTypeControllerTest {

    @Mock
    private ActionTypeService actionTypeService;

    @InjectMocks
    private ActionTypeController actionTypeController;

    private ActionTypeDto testActionTypeDto;
    private CreateActionTypeDto testCreateActionTypeDto;
    private Page<ActionTypeDto> testPage;
    private List<ActionTypeDto> testList;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        testActionTypeDto = new ActionTypeDto(1L, "Test Action");
        testCreateActionTypeDto = new CreateActionTypeDto("New Action");

        testList = Arrays.asList(
            new ActionTypeDto(1L, "Action 1"),
            new ActionTypeDto(2L, "Action 2")
        );

        Pageable pageable = PageRequest.of(0, 10);
        testPage = new PageImpl<>(testList, pageable, testList.size());
    }

    @Test
    @DisplayName("Создание ActionType должно возвращать созданный DTO")
    void createActionType_Success() {
        // Given
        when(actionTypeService.createActionType(any(CreateActionTypeDto.class)))
            .thenReturn(testActionTypeDto);

        // When
        ActionTypeDto result = actionTypeController.createActionType(testCreateActionTypeDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Action");
        verify(actionTypeService, times(1)).createActionType(testCreateActionTypeDto);
    }

    @Test
    @DisplayName("Получение списка ActionType должно возвращать страницу")
    void listActionTypes_Success() {
        // Given
        String nameFilter = "test";
        Pageable pageable = PageRequest.of(0, 10);
        when(actionTypeService.listActionTypes(eq(nameFilter), any(Pageable.class)))
            .thenReturn(testPage);

        // When
        Page<ActionTypeDto> result = actionTypeController.listActionTypes(nameFilter, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        verify(actionTypeService, times(1)).listActionTypes(nameFilter, pageable);
    }

    @Test
    @DisplayName("Получение списка ActionType без фильтра должно работать")
    void listActionTypes_WithoutFilter_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        when(actionTypeService.listActionTypes(isNull(), any(Pageable.class)))
            .thenReturn(testPage);

        // When
        Page<ActionTypeDto> result = actionTypeController.listActionTypes(null, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        verify(actionTypeService, times(1)).listActionTypes(isNull(), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение отсортированного списка ActionType по возрастанию")
    void listAllActionTypesSorted_Ascending_Success() {
        // Given
        when(actionTypeService.listAllActionTypesSorted(true))
            .thenReturn(testList);

        // When
        List<ActionTypeDto> result = actionTypeController.listAllActionTypesSorted(true);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(actionTypeService, times(1)).listAllActionTypesSorted(true);
    }

    @Test
    @DisplayName("Получение отсортированного списка ActionType по убыванию")
    void listAllActionTypesSorted_Descending_Success() {
        // Given
        when(actionTypeService.listAllActionTypesSorted(false))
            .thenReturn(testList);

        // When
        List<ActionTypeDto> result = actionTypeController.listAllActionTypesSorted(false);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(actionTypeService, times(1)).listAllActionTypesSorted(false);
    }

    @Test
    @DisplayName("Получение отсортированного списка без параметра должно использовать возрастание")
    void listAllActionTypesSorted_NullParameter_Success() {
        // Given
        when(actionTypeService.listAllActionTypesSorted(true))
            .thenReturn(testList);

        // When
        List<ActionTypeDto> result = actionTypeController.listAllActionTypesSorted(null);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(actionTypeService, times(1)).listAllActionTypesSorted(true);
    }

    @Test
    @DisplayName("Получение ActionType по ID должно возвращать DTO")
    void getActionTypeById_Success() {
        // Given
        Long id = 1L;
        when(actionTypeService.getActionTypeById(id))
            .thenReturn(testActionTypeDto);

        // When
        ActionTypeDto result = actionTypeController.getActionTypeById(id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Action");
        verify(actionTypeService, times(1)).getActionTypeById(id);
    }

    @Test
    @DisplayName("Получение ActionType по имени должно возвращать DTO")
    void getActionTypeByName_Success() {
        // Given
        String name = "Test Action";
        when(actionTypeService.getActionTypeByName(name))
            .thenReturn(testActionTypeDto);

        // When
        ActionTypeDto result = actionTypeController.getActionTypeByName(name);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Action");
        verify(actionTypeService, times(1)).getActionTypeByName(name);
    }

    @Test
    @DisplayName("Обновление ActionType должно возвращать обновленный DTO")
    void updateActionType_Success() {
        // Given
        Long id = 1L;
        ActionTypeDto updateDto = new ActionTypeDto(id, "Updated Action");
        when(actionTypeService.updateActionType(eq(id), any(ActionTypeDto.class)))
            .thenReturn(updateDto);

        // When
        ActionTypeDto result = actionTypeController.updateActionType(id, updateDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo("Updated Action");
        verify(actionTypeService, times(1)).updateActionType(id, updateDto);
    }

    @Test
    @DisplayName("Удаление ActionType должно вызывать сервис без исключений")
    void deleteActionType_Success() {
        // Given
        Long id = 1L;
        doNothing().when(actionTypeService).deleteActionType(id);

        // When & Then
        actionTypeController.deleteActionType(id);

        verify(actionTypeService, times(1)).deleteActionType(id);
    }

    @Test
    @DisplayName("Обработка DuplicateActionTypeNameException должна возвращать 409 статус")
    void handleDuplicateName_Success() {
        // Given
        String errorMessage = "ActionType with name 'Test' already exists";
        DuplicateActionTypeNameException exception = new DuplicateActionTypeNameException("Test");

        // When
        ResponseEntity<String> result = actionTypeController.handleDuplicateName(exception);

        // Then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(result.getBody()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Получение пустого списка ActionType должно работать")
    void listActionTypes_EmptyList_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ActionTypeDto> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(actionTypeService.listActionTypes(anyString(), any(Pageable.class)))
            .thenReturn(emptyPage);

        // When
        Page<ActionTypeDto> result = actionTypeController.listActionTypes("nonexistent", pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(0);
        verify(actionTypeService, times(1)).listActionTypes("nonexistent", pageable);
    }

    @Test
    @DisplayName("Получение пустого отсортированного списка должно работать")
    void listAllActionTypesSorted_EmptyList_Success() {
        // Given
        when(actionTypeService.listAllActionTypesSorted(anyBoolean()))
            .thenReturn(Collections.emptyList());

        // When
        List<ActionTypeDto> result = actionTypeController.listAllActionTypesSorted(true);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(actionTypeService, times(1)).listAllActionTypesSorted(true);
    }

    @Test
    @DisplayName("Проверка вызова сервиса с правильными параметрами")
    void serviceCalls_WithCorrectParameters() {
        // Given
        Long id = 1L;
        String name = "Test";
        Pageable pageable = PageRequest.of(0, 10);

        // When
        actionTypeController.getActionTypeById(id);
        actionTypeController.getActionTypeByName(name);
        actionTypeController.listActionTypes(name, pageable);
        actionTypeController.listAllActionTypesSorted(true);
        actionTypeController.deleteActionType(id);

        // Then
        verify(actionTypeService, times(1)).getActionTypeById(id);
        verify(actionTypeService, times(1)).getActionTypeByName(name);
        verify(actionTypeService, times(1)).listActionTypes(name, pageable);
        verify(actionTypeService, times(1)).listAllActionTypesSorted(true);
        verify(actionTypeService, times(1)).deleteActionType(id);
    }

    @Test
    @DisplayName("Проверка работы контроллера с null значениями")
    void controllerHandlesNullValues_Success() {
        // Given
        when(actionTypeService.listActionTypes(isNull(), any(Pageable.class)))
            .thenReturn(testPage);

        // When
        Page<ActionTypeDto> result = actionTypeController.listActionTypes(null, PageRequest.of(0, 10));

        // Then
        assertThat(result).isNotNull();
        verify(actionTypeService, times(1)).listActionTypes(isNull(), any(Pageable.class));
    }
}
