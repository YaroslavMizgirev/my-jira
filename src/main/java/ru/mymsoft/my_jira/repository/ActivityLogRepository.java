package ru.mymsoft.my_jira.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.ActivityLog;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

  // Найти все активности по задаче
  List<ActivityLog> findByIssueIdOrderByCreatedAtDesc(Long issueId);

  // Найти активности по пользователю
  List<ActivityLog> findByUserIdOrderByCreatedAtDesc(Long userId);

  // Найти активности по типу действия
  List<ActivityLog> findByActionTypeIdOrderByCreatedAtDesc(Long actionTypeId);

  // Найти последние активности по задаче (с пагинацией)
  List<ActivityLog> findTop10ByIssueIdOrderByCreatedAtDesc(Long issueId);

  // Поиск по измененному полю
  List<ActivityLog> findByIssueIdAndFieldNameOrderByCreatedAtDesc(Long issueId, String fieldName);

  // Найти активности за период
  List<ActivityLog> findIssueActivitiesInPeriod(Long issueId, Instant startDate, Instant endDate);

  // Удалить старые активности (для cleanup) - опасно!
  // void deleteByCreatedAtBefore(Instant date);
  // Если оставляешь - добав @Transactional и проверку: Например
  // @Transactional
  // @Modifying
  // @Query("DELETE FROM ActionType a WHERE a.name = :name AND NOT EXISTS (SELECT 1 FROM ActivityLog al WHERE al.actionType = a)")
  // int deleteByNameIfNotUsed(@Param("name") String name);

  @Query("SELECT COUNT(al) FROM ActivityLog al WHERE al.issue.project.id = :projectId")
  Long countActivitiesByProject(@Param("projectId") Long projectId);

  @Query("SELECT al FROM ActivityLog al JOIN FETCH al.user WHERE al.issue.id = :issueId")
  List<ActivityLog> findByIssueIdWithUser(@Param("issueId") Long issueId);
}
