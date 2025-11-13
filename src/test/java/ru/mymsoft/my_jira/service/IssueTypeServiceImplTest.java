package com.mymsoft.myJIRA.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.mymsoft.my_jira.dto.IssueTypeCreateDto;
import ru.mymsoft.my_jira.model.IssueType;
import ru.mymsoft.my_jira.repository.IssueTypeRepository;
import ru.mymsoft.my_jira.service.IssueTypeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssueTypeServiceImplTest {

    @Mock
    private IssueTypeRepository issueTypeRepository;

    @InjectMocks
    private IssueTypeService issueTypeService;

    @BeforeEach
    void setUp() {
        // Здесь можно настроить базовое поведение моков
    }

    @Test
    void shouldCreateNewIssueTypeSuccessfully() {
        // 1. Arrange (Подготовка данных)
        IssueTypeCreateDto createDto = new IssueTypeCreateDto();
        createDto.setName("Bug");
        createDto.setIconUrl("/icons/bug.png");
        createDto.setColorHexCode("#FF0000");

        // Предполагаемый объект IssueType, который будет сохранен
        IssueType savedIssueType = new IssueType();
        savedIssueType.setId(1L);
        savedIssueType.setName(createDto.getName());
        savedIssueType.setIconUrl(createDto.getIconUrl());
        savedIssueType.setColorHexCode(createDto.getColorHexCode());

        // Настройка поведения мок-объекта репозитория
        when(issueTypeRepository.save(any(IssueType.class))).thenReturn(savedIssueType);

        // 2. Act (Выполнение действия)
        IssueType createdIssueType = issueTypeService.createIssueType(createDto);

        // 3. Assert (Проверка результатов)
        assertNotNull(createdIssueType, "Созданный тип задачи не должен быть null");
        assertNotNull(createdIssueType.getId(), "ID типа задачи должен быть сгенерирован");
        assertEquals(1L, createdIssueType.getId(), "ID типа задачи должен быть 1"); // Проверяем конкретный ID
        assertEquals("Bug", createdIssueType.getName(), "Название типа задачи должно соответствовать");
        assertEquals("/icons/bug.png", createdIssueType.getIconUrl(), "URL иконки должен соответствовать");
        assertEquals("#FF0000", createdIssueType.getColorHexCode(), "Код цвета должен соответствовать");
    }
}