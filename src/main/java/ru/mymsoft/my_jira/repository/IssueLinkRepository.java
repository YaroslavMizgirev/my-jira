package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.IssueLink;

@Repository
public interface IssueLinkRepository extends JpaRepository<IssueLink, Long> {

  // Найти все исходящие связи задачи
  List<IssueLink> findBySourceIssueIdOrderByCreatedAtDesc(Long sourceIssueId);

  // Найти все входящие связи задачи
  List<IssueLink> findByTargetIssueIdOrderByCreatedAtDesc(Long targetIssueId);

  // Найти все связи задачи (и входящие и исходящие)
  @Query("SELECT il FROM IssueLink il WHERE il.sourceIssue.id = :issueId OR il.targetIssue.id = :issueId ORDER BY il.createdAt DESC")
  List<IssueLink> findAllByIssueId(@Param("issueId") Long issueId);

  // Найти связи по типу связи
  List<IssueLink> findByLinkTypeIdOrderByCreatedAtDesc(Long linkTypeId);

  // Найти конкретную связь между двумя задачами
  Optional<IssueLink> findBySourceIssueIdAndTargetIssueIdAndLinkTypeId(Long sourceIssueId, Long targetIssueId, Long linkTypeId);

  // Проверить существование связи между задачами
  boolean existsBySourceIssueIdAndTargetIssueIdAndLinkTypeId(Long sourceIssueId, Long targetIssueId, Long linkTypeId);

  // Проверить, есть ли любая связь между задачами
  boolean existsBySourceIssueIdAndTargetIssueId(Long sourceIssueId, Long targetIssueId);

  // Количество исходящих связей у задачи
  Long countBySourceIssueId(Long sourceIssueId);

  // Количество входящих связей у задачи
  Long countByTargetIssueId(Long targetIssueId);

  // Количество связей по типам
  @Query("SELECT il.linkType.name, COUNT(il) FROM IssueLink il GROUP BY il.linkType.id, il.linkType.name")
  List<Object[]> countLinksByType();

  // Найти блокирующие связи (например, тип "blocks")
  @Query("SELECT il FROM IssueLink il JOIN il.linkType lt WHERE lt.name = 'blocks' AND il.sourceIssue.id = :issueId")
  List<IssueLink> findBlocksByIssueId(@Param("issueId") Long issueId);

  // Найти связи, которые блокируют задачу
  @Query("SELECT il FROM IssueLink il JOIN il.linkType lt WHERE lt.name = 'blocks' AND il.targetIssue.id = :issueId")
  List<IssueLink> findBlockedByIssueId(@Param("issueId") Long issueId);

  // Найти дублирующие задачи
  @Query("SELECT il FROM IssueLink il JOIN il.linkType lt WHERE lt.name = 'duplicates' AND (il.sourceIssue.id = :issueId OR il.targetIssue.id = :issueId)")
  List<IssueLink> findDuplicatesByIssueId(@Param("issueId") Long issueId);

  // Найти взаимные связи (обе стороны)
  @Query("SELECT il FROM IssueLink il WHERE (il.sourceIssue.id = :issueId1 AND il.targetIssue.id = :issueId2) OR (il.sourceIssue.id = :issueId2 AND il.targetIssue.id = :issueId1)")
  List<IssueLink> findBidirectionalLinks(@Param("issueId1") Long issueId1, @Param("issueId2") Long issueId2);

  // Найти связи с полной информацией (JOIN FETCH)
  @Query("SELECT il FROM IssueLink il JOIN FETCH il.sourceIssue JOIN FETCH il.targetIssue JOIN FETCH il.linkType WHERE il.sourceIssue.id = :issueId OR il.targetIssue.id = :issueId")
  List<IssueLink> findAllByIssueIdWithDetails(@Param("issueId") Long issueId);

  // Найти циклические связи (для валидации)
  @Query("SELECT il1, il2 FROM IssueLink il1, IssueLink il2 WHERE il1.sourceIssue.id = il2.targetIssue.id AND il1.targetIssue.id = il2.sourceIssue.id AND il1.id <> il2.id")
  List<Object[]> findCircularLinks();

  // Поиск связей в проекте
  @Query("SELECT il FROM IssueLink il WHERE (il.sourceIssue.project.id = :projectId OR il.targetIssue.project.id = :projectId)")
  List<IssueLink> findByProjectId(@Param("projectId") Long projectId);
}
