package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateNotificationTemplateDto;
import ru.mymsoft.my_jira.dto.NotificationTemplateDto;
import ru.mymsoft.my_jira.dto.UpdateNotificationTemplateDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.NotificationTemplate;
import ru.mymsoft.my_jira.repository.NotificationTemplateRepository;

@Service
@RequiredArgsConstructor
public class NotificationTemplateService {

    private final NotificationTemplateRepository notificationTemplateRepository;

    @Transactional
    public NotificationTemplateDto create(CreateNotificationTemplateDto request) {
        if (notificationTemplateRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("NotificationTemplate", "name", request.name());
        }
        NotificationTemplate saved = notificationTemplateRepository.save(
            NotificationTemplate.builder()
                .name(request.name())
                .subjectTemplate(request.subjectTemplate())
                .bodyTemplate(request.bodyTemplate())
                .templateType(request.templateType())
                .isActive(request.isActive())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public NotificationTemplateDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<NotificationTemplateDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return notificationTemplateRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public NotificationTemplateDto update(Long id, UpdateNotificationTemplateDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        NotificationTemplate existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && notificationTemplateRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("NotificationTemplate", "name", request.name());
        }
        existing.setName(request.name());
        existing.setSubjectTemplate(request.subjectTemplate());
        existing.setBodyTemplate(request.bodyTemplate());
        existing.setTemplateType(request.templateType());
        existing.setIsActive(request.isActive());
        return toDto(notificationTemplateRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!notificationTemplateRepository.existsById(id)) {
            throw new ResourceNotFoundException("NotificationTemplate", "id", id);
        }
        notificationTemplateRepository.deleteById(id);
    }

    private NotificationTemplate findOrThrow(Long id) {
        return notificationTemplateRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("NotificationTemplate", "id", id));
    }

    private NotificationTemplateDto toDto(NotificationTemplate e) {
        return new NotificationTemplateDto(
            e.getId(), e.getName(), e.getSubjectTemplate(),
            e.getBodyTemplate(), e.getTemplateType(), e.getIsActive(),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
