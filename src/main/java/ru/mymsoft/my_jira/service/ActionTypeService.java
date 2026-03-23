package ru.mymsoft.my_jira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.ActionTypeDto;
import ru.mymsoft.my_jira.dto.CreateActionTypeDto;
import ru.mymsoft.my_jira.model.ActionType;
import ru.mymsoft.my_jira.repository.ActionTypeRepository;
import ru.mymsoft.my_jira.exception.DuplicateActionTypeNameException;

@Service
@RequiredArgsConstructor
public class ActionTypeService {
    @Autowired
    private final ActionTypeRepository actionTypeRepository;

    @Transactional
    public ActionTypeDto createActionType(CreateActionTypeDto createActionTypeDto) {
        validateCreateActionTypeDto(createActionTypeDto);
        
        if (actionTypeRepository.existsByName(createActionTypeDto.name())) {
            throw new DuplicateActionTypeNameException(createActionTypeDto.name());
        }

        ActionType actionType = ActionType.builder()
            .name(createActionTypeDto.name())
            .build();
        ActionType savedActionType = actionTypeRepository.save(actionType);
        return toDto(savedActionType);
    }

    @Transactional(readOnly = true)
    public ActionTypeDto getActionTypeById(Long id) {
        validateId(id);
        
        ActionType actionType = actionTypeRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                MessageFormat.format("ActionType with ID: {0} - not found", id))
            );
        return toDto(actionType);
    }

    @Transactional(readOnly = true)
    public ActionTypeDto getActionTypeByName(String name) {
        validateName(name);
        
        ActionType actionType = actionTypeRepository
            .findByName(name)
            .orElseThrow(() -> new IllegalArgumentException(
                MessageFormat.format("ActionType with name: {0} - not found", name))
            );
        return toDto(actionType);
    }

    @Transactional(readOnly = true)
    public Page<ActionTypeDto> listActionTypes(String namePart, Pageable pageable) {
        validatePageable(pageable);
        
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
        return actionTypes
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Transactional
    public ActionTypeDto updateActionType(Long actionTypeId, ActionTypeDto updateActionTypeDto) {
        validateId(actionTypeId);
        validateActionTypeDto(updateActionTypeDto);
        
        if (!actionTypeId.equals(updateActionTypeDto.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }

        ActionType existingActionType = actionTypeRepository
            .findById(actionTypeId)
            .orElseThrow(() -> new IllegalArgumentException(
                MessageFormat.format("ActionType with ID: {0} - not found", actionTypeId))
            );

        if (updateActionTypeDto.name() != null && !updateActionTypeDto.name().trim().isEmpty()) {
            if (!updateActionTypeDto.name().equals(existingActionType.getName()) &&
                actionTypeRepository.existsByName(updateActionTypeDto.name())
            ) {
                throw new DuplicateActionTypeNameException(updateActionTypeDto.name());
            }
            existingActionType.setName(updateActionTypeDto.name());
        }

        ActionType savedActionType = actionTypeRepository.save(existingActionType);
        return toDto(savedActionType);
    }

    @Transactional
    public void deleteActionType(Long actionTypeId) {
        validateId(actionTypeId);
        
        if (!actionTypeRepository.existsById(actionTypeId)) {
            throw new IllegalArgumentException(
                MessageFormat.format("ActionType with ID: {0} - not found", actionTypeId)
            );
        }
        actionTypeRepository.deleteById(actionTypeId);
    }

    private ActionTypeDto toDto(ActionType actionType) {
        return new ActionTypeDto(actionType.getId(), actionType.getName());
    }

    // Private validation methods
    
    private void validateCreateActionTypeDto(CreateActionTypeDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CreateActionTypeDto cannot be null");
        }
        if (dto.name() == null || dto.name().trim().isEmpty()) {
            throw new IllegalArgumentException("ActionType name cannot be null or empty");
        }
        if (dto.name().length() > 100) {
            throw new IllegalArgumentException("ActionType name cannot exceed 100 characters");
        }
    }

    private void validateActionTypeDto(ActionTypeDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ActionTypeDto cannot be null");
        }
        if (dto.id() == null) {
            throw new IllegalArgumentException("ActionType ID cannot be null");
        }
        if (dto.name() != null && dto.name().length() > 100) {
            throw new IllegalArgumentException("ActionType name cannot exceed 100 characters");
        }
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name cannot exceed 100 characters");
        }
    }

    private void validatePageable(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable cannot be null");
        }
        if (pageable.getPageNumber() < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (pageable.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }
    }
}