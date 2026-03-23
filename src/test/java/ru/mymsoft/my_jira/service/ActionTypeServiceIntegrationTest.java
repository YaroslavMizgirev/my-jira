package ru.mymsoft.my_jira.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import ru.mymsoft.my_jira.dto.ActionTypeDto;
import ru.mymsoft.my_jira.dto.CreateActionTypeDto;
import ru.mymsoft.my_jira.exception.DuplicateActionTypeNameException;
import ru.mymsoft.my_jira.model.ActionType;
import ru.mymsoft.my_jira.repository.ActionTypeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:testdb"
})
@DisplayName("ActionTypeService Integration Tests")
class ActionTypeServiceIntegrationTest {

    @Autowired
    private ActionTypeService actionTypeService;

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    private ActionType persistedActionType;

    @BeforeEach
    @Transactional
    void setUp() {
        actionTypeRepository.deleteAll();
        persistedActionType = ActionType.builder()
                .name("Existing Action")
                .build();
        persistedActionType = actionTypeRepository.save(persistedActionType);
    }

    @Test
    @DisplayName("Should create and persist action type successfully")
    void createActionType_Success() {
        // Given
        CreateActionTypeDto createDto = new CreateActionTypeDto("New Action Type");

        // When
        ActionTypeDto result = actionTypeService.createActionType(createDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo("New Action Type");

        ActionType persisted = actionTypeRepository.findById(result.id()).orElse(null);
        assertThat(persisted).isNotNull();
        assertThat(persisted.getName()).isEqualTo("New Action Type");
    }

    @Test
    @DisplayName("Should throw exception when creating duplicate action type")
    void createActionType_ThrowsException_WhenDuplicate() {
        // Given
        CreateActionTypeDto createDto = new CreateActionTypeDto("Existing Action");

        // When & Then
        assertThatThrownBy(() -> actionTypeService.createActionType(createDto))
                .isInstanceOf(DuplicateActionTypeNameException.class);

        long count = actionTypeRepository.count();
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Should retrieve action type by ID")
    void getActionTypeById_Success() {
        // When
        ActionTypeDto result = actionTypeService.getActionTypeById(persistedActionType.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(persistedActionType.getId());
        assertThat(result.name()).isEqualTo("Existing Action");
    }

    @Test
    @DisplayName("Should retrieve action type by name")
    void getActionTypeByName_Success() {
        // When
        ActionTypeDto result = actionTypeService.getActionTypeByName("Existing Action");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(persistedActionType.getId());
        assertThat(result.name()).isEqualTo("Existing Action");
    }

    @Test
    @DisplayName("Should list action types with pagination")
    void listActionTypes_WithPagination_Success() {
        // Given
        for (int i = 1; i <= 5; i++) {
            ActionType actionType = ActionType.builder()
                    .name("Action " + i)
                    .build();
            actionTypeRepository.save(actionType);
        }

        Pageable pageable = PageRequest.of(0, 3);

        // When — "Action " (with space) matches "Action 1..5" but not "Existing Action"
        Page<ActionTypeDto> result = actionTypeService.listActionTypes("Action ", pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getTotalElements()).isEqualTo(5);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should list all action types sorted ascending")
    void listAllActionTypesSorted_Ascending_Success() {
        // Given
        ActionType actionTypeB = ActionType.builder().name("B Action").build();
        ActionType actionTypeA = ActionType.builder().name("A Action").build();
        actionTypeRepository.save(actionTypeB);
        actionTypeRepository.save(actionTypeA);

        // When
        List<ActionTypeDto> result = actionTypeService.listAllActionTypesSorted(true);

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).name()).isEqualTo("A Action");
        assertThat(result.get(1).name()).isEqualTo("B Action");
        assertThat(result.get(2).name()).isEqualTo("Existing Action");
    }

    @Test
    @DisplayName("Should list all action types sorted descending")
    void listAllActionTypesSorted_Descending_Success() {
        // Given
        ActionType actionTypeB = ActionType.builder().name("B Action").build();
        ActionType actionTypeA = ActionType.builder().name("A Action").build();
        actionTypeRepository.save(actionTypeB);
        actionTypeRepository.save(actionTypeA);

        // When
        List<ActionTypeDto> result = actionTypeService.listAllActionTypesSorted(false);

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).name()).isEqualTo("Existing Action");
        assertThat(result.get(1).name()).isEqualTo("B Action");
        assertThat(result.get(2).name()).isEqualTo("A Action");
    }

    @Test
    @DisplayName("Should update action type successfully")
    void updateActionType_Success() {
        // Given
        ActionTypeDto updateDto = new ActionTypeDto(persistedActionType.getId(), "Updated Action");

        // When
        ActionTypeDto result = actionTypeService.updateActionType(persistedActionType.getId(), updateDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(persistedActionType.getId());
        assertThat(result.name()).isEqualTo("Updated Action");

        ActionType updated = actionTypeRepository.findById(persistedActionType.getId()).orElse(null);
        assertThat(updated.getName()).isEqualTo("Updated Action");
    }

    @Test
    @DisplayName("Should delete action type successfully")
    void deleteActionType_Success() {
        // When
        actionTypeService.deleteActionType(persistedActionType.getId());

        // Then
        ActionType deleted = actionTypeRepository.findById(persistedActionType.getId()).orElse(null);
        assertThat(deleted).isNull();

        long count = actionTypeRepository.count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("Should handle case-insensitive search correctly")
    void listActionTypes_CaseInsensitiveSearch_Success() {
        // Given
        ActionType actionType1 = ActionType.builder().name("Test Action").build();
        ActionType actionType2 = ActionType.builder().name("test action").build();
        ActionType actionType3 = ActionType.builder().name("TEST ACTION").build();
        actionTypeRepository.save(actionType1);
        actionTypeRepository.save(actionType2);
        actionTypeRepository.save(actionType3);

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<ActionTypeDto> result = actionTypeService.listActionTypes("test", pageable);

        // Then
        assertThat(result.getContent()).hasSize(3);
        result.getContent().forEach(dto -> 
                assertThat(dto.name().toLowerCase()).contains("test"));
    }

    @Test
    @DisplayName("Should handle empty filter correctly")
    void listActionTypes_EmptyFilter_Success() {
        // Given
        ActionType actionType1 = ActionType.builder().name("Action 1").build();
        ActionType actionType2 = ActionType.builder().name("Action 2").build();
        actionTypeRepository.save(actionType1);
        actionTypeRepository.save(actionType2);

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<ActionTypeDto> result = actionTypeService.listActionTypes("", pageable);

        // Then
        assertThat(result.getContent()).hasSize(3); // Including the one from setUp
    }

    @Test
    @DisplayName("Should maintain data consistency during concurrent operations")
    void concurrentOperations_MaintainConsistency() {
        // Given
        CreateActionTypeDto createDto = new CreateActionTypeDto("Concurrent Action");

        // When
        ActionTypeDto result1 = actionTypeService.createActionType(createDto);
        ActionTypeDto result2 = actionTypeService.getActionTypeById(result1.id());

        // Then
        assertThat(result1.id()).isEqualTo(result2.id());
        assertThat(result1.name()).isEqualTo(result2.name());
    }
}
