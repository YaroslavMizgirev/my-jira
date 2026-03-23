package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateIssueLinkDto;
import ru.mymsoft.my_jira.dto.IssueLinkDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Issue;
import ru.mymsoft.my_jira.model.IssueLink;
import ru.mymsoft.my_jira.model.IssueLinkType;
import ru.mymsoft.my_jira.repository.IssueLinkRepository;
import ru.mymsoft.my_jira.repository.IssueLinkTypeRepository;
import ru.mymsoft.my_jira.repository.IssueRepository;

@Service
@RequiredArgsConstructor
public class IssueLinkService {

    private final IssueLinkRepository issueLinkRepository;
    private final IssueRepository issueRepository;
    private final IssueLinkTypeRepository issueLinkTypeRepository;

    @Transactional
    public IssueLinkDto create(CreateIssueLinkDto request) {
        Issue source = issueRepository.findById(request.sourceIssueId())
            .orElseThrow(() -> new ResourceNotFoundException("Issue", "id", request.sourceIssueId()));
        Issue target = issueRepository.findById(request.targetIssueId())
            .orElseThrow(() -> new ResourceNotFoundException("Issue", "id", request.targetIssueId()));
        IssueLinkType linkType = issueLinkTypeRepository.findById(request.linkTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("IssueLinkType", "id", request.linkTypeId()));
        if (issueLinkRepository.existsBySourceIssueIdAndTargetIssueIdAndLinkTypeId(
                request.sourceIssueId(), request.targetIssueId(), request.linkTypeId())) {
            throw new DuplicateResourceException("IssueLink already exists for this combination");
        }
        IssueLink saved = issueLinkRepository.save(IssueLink.builder()
            .sourceIssue(source)
            .targetIssue(target)
            .linkType(linkType)
            .build());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<IssueLinkDto> listByIssue(Long issueId) {
        if (!issueRepository.existsById(issueId)) {
            throw new ResourceNotFoundException("Issue", "id", issueId);
        }
        return issueLinkRepository.findAllByIssueId(issueId).stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public IssueLinkDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!issueLinkRepository.existsById(id)) {
            throw new ResourceNotFoundException("IssueLink", "id", id);
        }
        issueLinkRepository.deleteById(id);
    }

    private IssueLink findOrThrow(Long id) {
        return issueLinkRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("IssueLink", "id", id));
    }

    private IssueLinkDto toDto(IssueLink l) {
        return new IssueLinkDto(
            l.getId(),
            l.getSourceIssue().getId(),
            l.getTargetIssue().getId(),
            l.getLinkType().getId(),
            l.getCreatedAt()
        );
    }
}
