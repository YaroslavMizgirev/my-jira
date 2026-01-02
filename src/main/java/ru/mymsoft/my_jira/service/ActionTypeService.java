package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.ActionTypeDto;
import ru.mymsoft.my_jira.dto.CreateActionTypeDto;
import ru.mymsoft.my_jira.dto.UpdateActionTypeDto;
import ru.mymsoft.my_jira.model.ActionType;
import ru.mymsoft.my_jira.repository.ActionTypeRepository;

@Service
@RequiredArgsConstructor
public class ActionTypeService {
    private final ActionTypeRepository actionTypeRepository;
    
    @Transactional
    public ActionTypeDto createActionType(CreateActionTypeDto createActionTypeDto) {
        if (actionTypeRepository.existsByName(createActionTypeDto.name())) {
            throw new IllegalArgumentException("ActionType with this name already exists");
        }
        ActionType actionType = ActionType.builder()
            .name(createActionTypeDto.name())
            .build();
        ActionType savedActionType = actionTypeRepository.save(actionType);
        return toDto(savedActionType);
    }

    @Transactional(readOnly = true)
    public ActionTypeDto getActionTypeById(Long id) {
        ActionType actionType = actionTypeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ActionType not found: " + id));
        return toDto(actionType);
    }

    @Transactional(readOnly = true)
    public ActionTypeDto getActionTypeByName(String name) {
        ActionType actionType = actionTypeRepository.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("ActionType not found with name: " + name));
        return toDto(actionType);
    }

    @Transactional(readOnly = true)
    public Page<ActionTypeDto> listActionTypes(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return actionTypeRepository
            .findAllByNameContainingIgnoreCase(filter, pageable)
            .map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<ActionTypeDto> listAllActionTypesSorted(boolean ascending) {
        List<ActionType> actionTypes = ascending
            ? actionTypeRepository.findAllByOrderByNameAsc()
            : actionTypeRepository.findAllByOrderByNameDesc();
        return actionTypes.stream()
            .map(this::toDto)
            .toList();
    }

    @Transactional
    public ActionTypeDto updateActionType(Long actionTypeId, UpdateActionTypeDto updateActionTypeDto) {
        // Проверяем соответствие ID
        if (!actionTypeId.equals(updateActionTypeDto.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }

        ActionType existingActionType = actionTypeRepository.findById(actionTypeId)
            .orElseThrow(() -> new IllegalArgumentException("ActionType not found: " + actionTypeId));

        if (updateActionTypeDto.name() != null && !updateActionTypeDto.name().trim().isEmpty()) {
            // Проверяем уникальность имени
            if (!updateActionTypeDto.name().equals(existingActionType.getName()) && 
                actionTypeRepository.existsByName(updateActionTypeDto.name())) {
                throw new IllegalArgumentException("ActionType with this name already exists");
            }
            existingActionType.setName(updateActionTypeDto.name());
        }

        ActionType savedActionType = actionTypeRepository.save(existingActionType);
        return toDto(savedActionType);
    }

    @Transactional
    public void deleteActionType(Long id) {
        if (!actionTypeRepository.existsById(id)) {
            throw new IllegalArgumentException("ActionType not found: " + id);
        }
        actionTypeRepository.deleteById(id);
    }

    private ActionTypeDto toDto(ActionType actionType) {
        return new ActionTypeDto(actionType.getId(), actionType.getName());
    }
}