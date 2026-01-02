package ru.mymsoft.my_jira.controller;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.ActionTypeDto;
import ru.mymsoft.my_jira.dto.CreateActionTypeDto;
import ru.mymsoft.my_jira.dto.UpdateActionTypeDto;
import ru.mymsoft.my_jira.service.ActionTypeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/action-types")
@RequiredArgsConstructor
@Tag(name = "Action Types", description = "API для управления типами действий")
public class ActionTypeController {
    private final ActionTypeService actionTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового типа действия")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Тип действия успешно создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные типа действия"),
        @ApiResponse(responseCode = "409", description = "Тип действия с таким именем уже существует")
    })
    public ActionTypeDto createActionType(@Valid @RequestBody CreateActionTypeDto request) {
        return actionTypeService.createActionType(request);
    }
    
    @GetMapping
    @Operation(summary = "Получение списка всех типов действий с фильтрацией")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список типов действий получен успешно")
    })
    public Page<ActionTypeDto> listActionTypes(
        @Parameter(description = "Фильтр по части имени типа действия (опционально)")
        @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return actionTypeService.listActionTypes(namePart, pageable);
    }

    @GetMapping("/all-sorted")
    @Operation(summary = "Получение списка всех типов действий, отсортированных по имени")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список типов действий получен успешно")
    })
    public List<ActionTypeDto> listAllActionTypesSorted(
        @Parameter(description = "Сортировка по имени: true - по возрастанию, false - по убыванию (опционально)")
        @RequestParam(required = false) Boolean ascending
    ) {
        boolean asc = ascending == null ? true : ascending;
        return actionTypeService.listAllActionTypesSorted(asc);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение типа действия по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Тип действия найден"),
        @ApiResponse(responseCode = "404", description = "Тип действия не найден")
    })
    public ActionTypeDto getActionTypeById(
        @Parameter(description = "ID типа действия", example = "1")
        @PathVariable Long id
    ) {
        return actionTypeService.getActionTypeById(id);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Получение типа действия по имени")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Тип действия найден"),
        @ApiResponse(responseCode = "404", description = "Тип действия не найден")
    })
    public ActionTypeDto getActionTypeByName(
        @Parameter(description = "Имя типа действия", example = "Bug")
        @PathVariable String name
    ) {
        return actionTypeService.getActionTypeByName(name);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление типа действия")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Тип действия успешно обновлен"),
        @ApiResponse(responseCode = "400", description = "Неверные данные типа действия"),
        @ApiResponse(responseCode = "404", description = "Тип действия не найден"),
        @ApiResponse(responseCode = "409", description = "Тип действия с таким именем уже существует")
    })
    public ActionTypeDto updateActionType(
        @Parameter(description = "ID типа действия", example = "1")
        @PathVariable Long id,
        @Valid @RequestBody UpdateActionTypeDto request
    ) {
        return actionTypeService.updateActionType(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление типа действия")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Тип действия успешно удален"),
        @ApiResponse(responseCode = "404", description = "Тип действия не найден")
    })
    public void deleteActionType(
        @Parameter(description = "ID типа действия", example = "1")
        @PathVariable Long id
    ) {
        actionTypeService.deleteActionType(id);
    }
}