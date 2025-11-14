package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.IssueWatcher;

@Repository
public interface IssueWatcherRepository extends JpaRepository<IssueWatcher, Long> {

  // Найти всех наблюдателей задачи
  List<IssueWatcher> findByIssueId(Long issueId);

  // Найти все задачи, за которыми наблюдает пользователь
  List<IssueWatcher> findByUserId(Long userId);

  // Проверить, наблюдает ли пользователь за задачей
  boolean existsByIssueIdAndUserId(Long issueId, Long userId);

  // Найти конкретную запись наблюдения
  Optional<IssueWatcher> findByIssueIdAndUserId(Long issueId, Long userId);

  // СТАТИСТИКА И КОЛИЧЕСТВО
  // Количество наблюдателей задачи
  Long countByIssueId(Long issueId);

  // Количество задач, за которыми наблюдает пользователь
  Long countByUserId(Long userId);

  // // МЕТОДЫ УДАЛЕНИЯ
  // // Перестать наблюдать за задачей
  // void deleteByIssueIdAndUserId(Long issueId, Long userId);

  // // Удалить всех наблюдателей задачи
  // void deleteByIssueId(Long issueId);

  // // Удалить все наблюдения пользователя
  // void deleteByUserId(Long userId);

  // МАССОВЫЕ ОПЕРАЦИИ
  // Добавить нескольких наблюдателей к задаче
  @Modifying
  @Query("INSERT INTO IssueWatcher (issue, user) SELECT i, u FROM Issue i, User u WHERE i.id = :issueId AND u.id IN :userIds")
  int addWatchersToIssue(@Param("issueId") Long issueId, @Param("userIds") List<Long> userIds);

  // Удалить нескольких наблюдателей из задачи
  @Modifying
  @Query("DELETE FROM IssueWatcher iw WHERE iw.issue.id = :issueId AND iw.user.id IN :userIds")
  int removeWatchersFromIssue(@Param("issueId") Long issueId, @Param("userIds") List<Long> userIds);

  // МЕТОДЫ ДЛЯ УВЕДОМЛЕНИЙ
  // Найти наблюдателей задачи с информацией о пользователе (для уведомлений)
  @Query("SELECT iw FROM IssueWatcher iw JOIN FETCH iw.user WHERE iw.issue.id = :issueId")
  List<IssueWatcher> findWatchersWithUserByIssueId(@Param("issueId") Long issueId);

  // Найти наблюдателей с настройками уведомлений (если добавишь связь)
  @Query("SELECT iw FROM IssueWatcher iw JOIN FETCH iw.user u JOIN UserNotificationSetting uns ON u.id = uns.user WHERE iw.issue.id = :issueId AND uns.notificationType = 'ISSUE_UPDATED' AND uns.isEnabled = true")
  List<IssueWatcher> findWatchersWithEnabledNotifications(@Param("issueId") Long issueId);

  // МЕТОДЫ ДЛЯ UI И ОТЧЕТОВ
  // Найти самые наблюдаемые задачи
  @Query("SELECT iw.issue.id, COUNT(iw) as watcherCount FROM IssueWatcher iw GROUP BY iw.issue.id ORDER BY watcherCount DESC")
  List<Object[]> findMostWatchedIssues(@Param("limit") int limit);

  // Найти самых активных наблюдателей
  @Query("SELECT iw.user.id, COUNT(iw) as watchedCount FROM IssueWatcher iw GROUP BY iw.user.id ORDER BY watchedCount DESC")
  List<Object[]> findMostActiveWatchers(@Param("limit") int limit);

  // ПОИСК И ФИЛЬТРАЦИЯ
  // Найти наблюдателей по проекту
  @Query("SELECT iw FROM IssueWatcher iw WHERE iw.issue.project.id = :projectId")
  List<IssueWatcher> findByProjectId(@Param("projectId") Long projectId);

  // Найти наблюдателей по типу задачи
  @Query("SELECT iw FROM IssueWatcher iw WHERE iw.issue.issueType.id = :issueTypeId")
  List<IssueWatcher> findByIssueTypeId(@Param("issueTypeId") Long issueTypeId);

  // ДОПОЛНИТЕЛЬНЫЕ ПОЛЕЗНЫЕ МЕТОДЫ
  // Найти общих наблюдателей между двумя задачами
  @Query("SELECT iw1.user FROM IssueWatcher iw1 JOIN IssueWatcher iw2 ON iw1.user.id = iw2.user.id WHERE iw1.issue.id = :issueId1 AND iw2.issue.id = :issueId2")
  List<Object[]> findCommonWatchers(@Param("issueId1") Long issueId1, @Param("issueId2") Long issueId2);

  // Найти задачи, за которыми наблюдают несколько пользователей
  @Query("SELECT iw.issue.id FROM IssueWatcher iw WHERE iw.user.id IN :userIds GROUP BY iw.issue.id HAVING COUNT(iw.user.id) = :userCount")
  List<Long> findIssuesWatchedByAllUsers(@Param("userIds") List<Long> userIds, @Param("userCount") long userCount);

  // МЕТОДЫ ДЛЯ МИГРАЦИИ И СИНХРОНИЗАЦИИ
  // Копировать наблюдателей из одной задачи в другую
  @Modifying
  @Query("INSERT INTO IssueWatcher (issue, user) SELECT :targetIssueId, iw.user FROM IssueWatcher iw WHERE iw.issue.id = :sourceIssueId")
  int copyWatchersFromIssue(@Param("sourceIssueId") Long sourceIssueId, @Param("targetIssueId") Long targetIssueId);

  // МЕТОДЫ ДЛЯ ПРОВЕРКИ ПРАВ ДОСТУПА
  // Проверить, может ли пользователь видеть наблюдателей задачи
  @Query("SELECT COUNT(iw) > 0 FROM IssueWatcher iw JOIN iw.issue i WHERE iw.issue.id = :issueId AND i.project.id IN (SELECT pm.project.id FROM ProjectMember pm WHERE pm.group.id IN (SELECT ug.group.id FROM UserGroup ug WHERE ug.user.id = :userId))")
  boolean canUserSeeWatchers(@Param("userId") Long userId, @Param("issueId") Long issueId);
}
