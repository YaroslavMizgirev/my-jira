package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

  List<Attachment> findByIssueIdOrderByCreatedAtDesc(Long issueId);

  List<Attachment> findByUploaderIdOrderByCreatedAtDesc(Long uploaderId);

  List<Attachment> findByFileTypeIdOrderByCreatedAtDesc(Long fileTypeId);

  List<Attachment> findTop10ByIssueIdOrderByCreatedAtDesc(Long issueId);

  List<Attachment> findByIssueIdAndCreatedAtBetweenOrderByCreatedAtDesc(Long issueId, Instant startDate, Instant endDate);

  // Поиск по имени файла (частичное совпадение)
  List<Attachment> findByFileNameContainingIgnoreCase(String fileName);

  // Поиск по размеру файла
  List<Attachment> findByFileSizeBytesGreaterThan(Long minSize);

  List<Attachment> findByFileSizeBytesLessThan(Long maxSize);

  // Поиск по типу контента (MIME type)
  @Query("SELECT a FROM Attachment a JOIN a.fileType ft WHERE ft.mimeType = :mimeType")
  List<Attachment> findByMimeType(@Param("mimeType") String mimeType);

  // Получить вложения с информацией о типе файла (JOIN FETCH)
  @Query("SELECT a FROM Attachment a JOIN FETCH a.fileType WHERE a.issue.id = :issueId")
  List<Attachment> findByIssueIdWithFileType(@Param("issueId") Long issueId);

  // Статистика по проекту
  @Query("SELECT COUNT(a) FROM Attachment a WHERE a.issue.project.id = :projectId")
  Long countByProjectId(@Param("projectId") Long projectId);

  // Для больших файлов пагинация:
  Page<Attachment> findByIssueId(Long issueId, Pageable pageable);
}
