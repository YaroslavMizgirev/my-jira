package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateIssueWatcherDto;
import ru.mymsoft.my_jira.dto.IssueWatcherDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Issue;
import ru.mymsoft.my_jira.model.IssueWatcher;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.repository.IssueRepository;
import ru.mymsoft.my_jira.repository.IssueWatcherRepository;
import ru.mymsoft.my_jira.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class IssueWatcherService {

    private final IssueWatcherRepository issueWatcherRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Transactional
    public IssueWatcherDto add(CreateIssueWatcherDto request) {
        Issue issue = issueRepository.findById(request.issueId())
            .orElseThrow(() -> new ResourceNotFoundException("Issue", "id", request.issueId()));
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.userId()));
        if (issueWatcherRepository.existsByIssueIdAndUserId(request.issueId(), request.userId())) {
            throw new DuplicateResourceException("User " + request.userId() + " is already watching issue " + request.issueId());
        }
        IssueWatcher saved = issueWatcherRepository.save(
            IssueWatcher.builder().issue(issue).user(user).build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<IssueWatcherDto> listByIssue(Long issueId) {
        if (!issueRepository.existsById(issueId)) {
            throw new ResourceNotFoundException("Issue", "id", issueId);
        }
        return issueWatcherRepository.findByIssueId(issueId).stream().map(this::toDto).toList();
    }

    @Transactional
    public void remove(Long issueId, Long userId) {
        if (!issueWatcherRepository.existsByIssueIdAndUserId(issueId, userId)) {
            throw new ResourceNotFoundException("IssueWatcher not found for issue " + issueId + " and user " + userId);
        }
        issueWatcherRepository.deleteByIssueIdAndUserId(issueId, userId);
    }

    private IssueWatcherDto toDto(IssueWatcher w) {
        return new IssueWatcherDto(w.getIssue().getId(), w.getUser().getId());
    }
}
