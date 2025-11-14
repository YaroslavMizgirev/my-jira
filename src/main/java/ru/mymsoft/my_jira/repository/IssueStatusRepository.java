package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.IssueStatus;

@Repository
public interface IssueStatusRepository extends JpaRepository<IssueStatus, Long> {

  Optional<IssueStatus> findByName(String name);
  boolean existsByName(String name);
  List<IssueStatus> findByNameContainingIgnoreCase(String namePart);
  List<IssueStatus> findAllByOrderByNameAsc();

  // Найти статусы по ID списком (для workflow)
  List<IssueStatus> findByIdIn(List<Long> ids);

  // Найти статусы по списку имен
  List<IssueStatus> findByNameIn(List<String> names);

  // ДЛЯ СИСТЕМНЫХ СТАТУСОВ (если добавишь флаг):
  // List<IssueStatus> findByIsSystemStatusTrueOrderByNameAsc();
  // List<IssueStatus> findByIsSystemStatusFalseOrderByNameAsc();

  // ДЛЯ СТАТУСОВ ПО УМОЛЧАНИЮ (если добавишь флаг):
  // Optional<IssueStatus> findByIsDefaultTrue();
  // List<IssueStatus> findByIsDefaultFalseOrderByNameAsc();

  // МЕТОДЫ ДЛЯ WORKFLOW СИСТЕМЫ:

  // Найти статусы, используемые в конкретном workflow
  @Query("SELECT ws.status FROM WorkflowStatus ws WHERE ws.workflow.id = :workflowId ORDER BY ws.status.name")
  List<IssueStatus> findByWorkflowId(@Param("workflowId") Long workflowId);

  // Найти статусы, НЕ используемые в workflow
  @Query("SELECT s FROM IssueStatus s WHERE s.id NOT IN (SELECT ws.status.id FROM WorkflowStatus ws WHERE ws.workflow.id = :workflowId) ORDER BY s.name")
  List<IssueStatus> findNotInWorkflow(@Param("workflowId") Long workflowId);

  // Статистика использования статусов
  @Query("SELECT s.name, COUNT(i) FROM IssueStatus s LEFT JOIN Issue i ON s.id = i.status.id GROUP BY s.id, s.name ORDER BY COUNT(i) DESC")
  List<Object[]> findStatusUsageStatistics();

  // ДЛЯ АВТОДОПОЛНЕНИЯ И ПОИСКА:

  // Поиск по началу имени (для автодополнения)
  List<IssueStatus> findByNameStartingWithIgnoreCase(String prefix);

  // Получить все с сортировкой по убыванию
  List<IssueStatus> findAllByOrderByNameDesc();

  // СПЕЦИАЛЬНЫЕ МЕТОДЫ ДЛЯ ЧАСТО ИСПОЛЬЗУЕМЫХ СТАТУСОВ:

  // Найти "открытые" статусы (по соглашению имен)
  @Query("SELECT s FROM IssueStatus s WHERE LOWER(s.name) LIKE '%open%' OR LOWER(s.name) LIKE '%new%' OR LOWER(s.name) LIKE '%todo%'")
  List<IssueStatus> findOpenStatuses();

  // Найти "закрытые" статусы
  @Query("SELECT s FROM IssueStatus s WHERE LOWER(s.name) LIKE '%close%' OR LOWER(s.name) LIKE '%done%' OR LOWER(s.name) LIKE '%resolved%'")
  List<IssueStatus> findClosedStatuses();

  // ДЛЯ ВАЛИДАЦИИ И АДМИНИСТРИРОВАНИЯ:

  // Проверить, используется ли статус в задачах
  @Query("SELECT COUNT(i) > 0 FROM Issue i WHERE i.status.id = :statusId")
  boolean isStatusUsedInIssues(@Param("statusId") Long statusId);

  // Проверить, используется ли статус в workflow переходах
  @Query("SELECT COUNT(wt) > 0 FROM WorkflowTransition wt WHERE wt.fromStatus.id = :statusId OR wt.toStatus.id = :statusId")
  boolean isStatusUsedInWorkflows(@Param("statusId") Long statusId);

  // Найти замену для удаляемого статуса
  @Query("SELECT s FROM IssueStatus s WHERE s.id <> :excludeStatusId AND NOT EXISTS (SELECT 1 FROM WorkflowTransition wt WHERE wt.fromStatus.id = s.id OR wt.toStatus.id = s.id) ORDER BY s.name")
  List<IssueStatus> findAvailableReplacementStatuses(@Param("excludeStatusId") Long excludeStatusId);
}
