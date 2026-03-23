package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CommentDto;
import ru.mymsoft.my_jira.dto.CreateCommentDto;
import ru.mymsoft.my_jira.dto.UpdateCommentDto;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Comment;
import ru.mymsoft.my_jira.model.Issue;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.repository.CommentRepository;
import ru.mymsoft.my_jira.repository.IssueRepository;
import ru.mymsoft.my_jira.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDto create(CreateCommentDto request) {
        Issue issue = issueRepository.findById(request.issueId())
            .orElseThrow(() -> new ResourceNotFoundException("Issue", "id", request.issueId()));
        User author = userRepository.findById(request.authorId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.authorId()));
        Comment saved = commentRepository.save(Comment.builder()
            .issue(issue)
            .author(author)
            .content(request.content())
            .build());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> listByIssue(Long issueId) {
        if (!issueRepository.existsById(issueId)) {
            throw new ResourceNotFoundException("Issue", "id", issueId);
        }
        return commentRepository.findByIssueIdOrderByCreatedAtDesc(issueId).stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CommentDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional
    public CommentDto update(Long id, UpdateCommentDto request) {
        Comment existing = findOrThrow(id);
        existing.setContent(request.content());
        return toDto(commentRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", "id", id);
        }
        commentRepository.deleteById(id);
    }

    private Comment findOrThrow(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    private CommentDto toDto(Comment c) {
        return new CommentDto(
            c.getId(),
            c.getIssue().getId(),
            c.getAuthor().getId(),
            c.getContent(),
            c.getCreatedAt(),
            c.getUpdatedAt()
        );
    }
}
