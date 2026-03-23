package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateFileTypeDto;
import ru.mymsoft.my_jira.dto.FileTypeDto;
import ru.mymsoft.my_jira.dto.UpdateFileTypeDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.FileType;
import ru.mymsoft.my_jira.repository.FileTypeRepository;

@Service
@RequiredArgsConstructor
public class FileTypeService {

    private final FileTypeRepository fileTypeRepository;

    @Transactional
    public FileTypeDto create(CreateFileTypeDto request) {
        if (fileTypeRepository.existsByExtension(request.extension())) {
            throw new DuplicateResourceException("FileType", "extension", request.extension());
        }
        if (fileTypeRepository.existsByMimeType(request.mimeType())) {
            throw new DuplicateResourceException("FileType", "mimeType", request.mimeType());
        }
        FileType saved = fileTypeRepository.save(
            FileType.builder()
                .extension(request.extension())
                .mimeType(request.mimeType())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public FileTypeDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<FileTypeDto> list(String extensionPart, Pageable pageable) {
        String filter = extensionPart == null ? "" : extensionPart;
        return fileTypeRepository.findByExtensionContainingIgnoreCase(filter).stream()
            .map(this::toDto)
            .collect(java.util.stream.Collectors.collectingAndThen(
                java.util.stream.Collectors.toList(),
                list -> {
                    int start = (int) pageable.getOffset();
                    int end = Math.min(start + pageable.getPageSize(), list.size());
                    return new org.springframework.data.domain.PageImpl<>(
                        start <= end ? list.subList(start, end) : java.util.Collections.emptyList(),
                        pageable, list.size()
                    );
                }
            ));
    }

    @Transactional
    public FileTypeDto update(Long id, UpdateFileTypeDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        FileType existing = findOrThrow(id);
        if (!existing.getExtension().equals(request.extension()) && fileTypeRepository.existsByExtension(request.extension())) {
            throw new DuplicateResourceException("FileType", "extension", request.extension());
        }
        if (!existing.getMimeType().equals(request.mimeType()) && fileTypeRepository.existsByMimeType(request.mimeType())) {
            throw new DuplicateResourceException("FileType", "mimeType", request.mimeType());
        }
        existing.setExtension(request.extension());
        existing.setMimeType(request.mimeType());
        return toDto(fileTypeRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!fileTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("FileType", "id", id);
        }
        fileTypeRepository.deleteById(id);
    }

    private FileType findOrThrow(Long id) {
        return fileTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FileType", "id", id));
    }

    private FileTypeDto toDto(FileType e) {
        return new FileTypeDto(e.getId(), e.getExtension(), e.getMimeType());
    }
}
