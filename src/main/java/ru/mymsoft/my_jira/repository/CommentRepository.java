package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Comment;
import ru.mymsoft.my_jira.model.Issue;
import ru.mymsoft.my_jira.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByIssueIdOrderByCreatedAtDesc(Long issueId);

  List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);

  // Пагинация для больших списков комментариев
  List<Comment> findTop10ByIssueIdOrderByCreatedAtDesc(Long issueId);

  // Поиск комментариев за период
  List<Comment> findByIssueIdAndCreatedAtBetweenOrderByCreatedAtDesc(Long issueId, Instant startDate, Instant endDate);

  // Поиск по содержимому (для модерации/поиска)
  List<Comment> findByContentContainingIgnoreCase(String content);

  // Получить комментарии с информацией об авторе (избегаем N+1)
  @Query("SELECT c FROM Comment c JOIN FETCH c.author WHERE c.issue.id = :issueId ORDER BY c.createdAt DESC")
  List<Comment> findByIssueIdWithAuthor(@Param("issueId") Long issueId);

  // Найти последний комментарий к задаче
  @Query("SELECT c FROM Comment c WHERE c.issue.id = :issueId ORDER BY c.createdAt DESC LIMIT 1")
  Optional<Comment> findLatestByIssueId(@Param("issueId") Long issueId);

  // Статистика по комментариям
  Long countByIssueId(Long issueId);
  Long countByAuthorId(Long authorId);

  // Поиск недавно обновленных комментариев
  List<Comment> findByUpdatedAtAfterOrderByUpdatedAtDesc(Instant date);

  // 🔍 Для модерации/администрирования:
  @Query("SELECT c FROM Comment c WHERE c.updatedAt > c.createdAt")
  List<Comment> findEditedComments();
}
