package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.ActivityLogDto;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.ActivityLog;
import ru.mymsoft.my_jira.repository.ActivityLogRepository;
import ru.mymsoft.my_jira.repository.IssueRepository;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final IssueRepository issueRepository;

    @Transactional(readOnly = true)
    public List<ActivityLogDto> listByIssue(Long issueId) {
        if (!issueRepository.existsById(issueId)) {
            throw new ResourceNotFoundException("Issue", "id", issueId);
        }
        return activityLogRepository.findByIssueIdOrderByCreatedAtDesc(issueId).stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ActivityLogDto getById(Long id) {
        ActivityLog log = activityLogRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ActivityLog", "id", id));
        return toDto(log);
    }

    private ActivityLogDto toDto(ActivityLog l) {
        return new ActivityLogDto(
            l.getId(),
            l.getIssue().getId(),
            l.getUser() != null ? l.getUser().getId() : null,
            l.getActionType().getId(),
            l.getFieldName(),
            l.getOldValue(),
            l.getNewValue(),
            l.getCreatedAt()
        );
    }
}
