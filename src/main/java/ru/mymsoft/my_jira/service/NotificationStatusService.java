package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateNotificationStatusDto;
import ru.mymsoft.my_jira.dto.NotificationStatusDto;
import ru.mymsoft.my_jira.dto.UpdateNotificationStatusDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.NotificationStatus;
import ru.mymsoft.my_jira.repository.NotificationStatusRepository;

@Service
@RequiredArgsConstructor
public class NotificationStatusService {

    private final NotificationStatusRepository notificationStatusRepository;

    @Transactional
    public NotificationStatusDto create(CreateNotificationStatusDto request) {
        if (notificationStatusRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("NotificationStatus", "name", request.name());
        }
        NotificationStatus saved = notificationStatusRepository.save(
            NotificationStatus.builder()
                .name(request.name())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public NotificationStatusDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<NotificationStatusDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return notificationStatusRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public NotificationStatusDto update(Long id, UpdateNotificationStatusDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        NotificationStatus existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && notificationStatusRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("NotificationStatus", "name", request.name());
        }
        existing.setName(request.name());
        return toDto(notificationStatusRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!notificationStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("NotificationStatus", "id", id);
        }
        notificationStatusRepository.deleteById(id);
    }

    private NotificationStatus findOrThrow(Long id) {
        return notificationStatusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("NotificationStatus", "id", id));
    }

    private NotificationStatusDto toDto(NotificationStatus e) {
        return new NotificationStatusDto(e.getId(), e.getName());
    }
}
