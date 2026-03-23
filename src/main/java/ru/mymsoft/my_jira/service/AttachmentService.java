package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.AttachmentDto;
import ru.mymsoft.my_jira.dto.CreateAttachmentDto;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Attachment;
import ru.mymsoft.my_jira.model.FileType;
import ru.mymsoft.my_jira.model.Issue;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.repository.AttachmentRepository;
import ru.mymsoft.my_jira.repository.FileTypeRepository;
import ru.mymsoft.my_jira.repository.IssueRepository;
import ru.mymsoft.my_jira.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final FileTypeRepository fileTypeRepository;

    @Transactional
    public AttachmentDto create(CreateAttachmentDto request) {
        Issue issue = issueRepository.findById(request.issueId())
            .orElseThrow(() -> new ResourceNotFoundException("Issue", "id", request.issueId()));
        User uploader = userRepository.findById(request.uploaderId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.uploaderId()));
        FileType fileType = fileTypeRepository.findById(request.fileTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("FileType", "id", request.fileTypeId()));
        Attachment saved = attachmentRepository.save(Attachment.builder()
            .issue(issue)
            .uploader(uploader)
            .fileName(request.fileName())
            .fileType(fileType)
            .fileSizeBytes(request.fileSizeBytes())
            .storagePath(request.storagePath())
            .description(request.description())
            .build());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AttachmentDto> listByIssue(Long issueId) {
        if (!issueRepository.existsById(issueId)) {
            throw new ResourceNotFoundException("Issue", "id", issueId);
        }
        return attachmentRepository.findByIssueIdOrderByUpdatedAtDesc(issueId).stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AttachmentDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!attachmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Attachment", "id", id);
        }
        attachmentRepository.deleteById(id);
    }

    private Attachment findOrThrow(Long id) {
        return attachmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Attachment", "id", id));
    }

    private AttachmentDto toDto(Attachment a) {
        return new AttachmentDto(
            a.getId(),
            a.getIssue().getId(),
            a.getUploader().getId(),
            a.getFileName(),
            a.getFileType().getId(),
            a.getFileSizeBytes(),
            a.getStoragePath(),
            a.getDescription(),
            a.getUpdatedAt()
        );
    }
}
