package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.IssueType;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {

  Optional<IssueType> findByName(String name);
  boolean existsByName(String name);

  // Поиск по части имени (для автодополнения)
  List<IssueType> findByNameContainingIgnoreCase(String namePart);
  List<IssueType> findByNameStartingWithIgnoreCase(String prefix);

  // МЕТОДЫ ДЛЯ ОТОБРАЖЕНИЯ
  // Получить все с сортировкой по имени
  List<IssueType> findAllByOrderByNameAsc();
  List<IssueType> findAllByOrderByNameDesc();

  // Получить с сортировкой по уровню/приоритету (если добавишь поле order)
  // List<IssueType> findAllByOrderByOrderAsc();

  // МЕТОДЫ ДЛЯ UI (иконки, цвета)

  // Найти типы с определенным цветом
  List<IssueType> findByColorHexCode(String colorHexCode);

  // Найти типы с иконками
  List<IssueType> findByIconUrlIsNotNull();
  List<IssueType> findByIconUrlIsNull();

  // ПОЛЕЗНЫЕ ДЛЯ СИСТЕМНЫХ ТИПОВ
  // Найти системные типы (если добавишь флаг isSystemType)
  // List<IssueType> findByIsSystemTypeTrue();
  // List<IssueType> findByIsSystemTypeFalse();

  // МЕТОДЫ ДЛЯ WORKFLOW И ПРОЕКТОВ
  // Найти типы задач по проекту (через issues)
  @Query("SELECT DISTINCT it FROM IssueType it JOIN Issue i ON it.id = i.issueType.id WHERE i.project.id = :projectId ORDER BY it.name")
  List<IssueType> findByProjectId(@Param("projectId") Long projectId);

  // Найти типы, используемые в workflow defaults проекта
  @Query("SELECT DISTINCT pitwd.issueType FROM ProjectIssueTypeWorkflowDefault pitwd WHERE pitwd.project.id = :projectId")
  List<IssueType> findTypesWithWorkflowDefaultsByProjectId(@Param("projectId") Long projectId);

  // СТАТИСТИКА И АНАЛИТИКА
  // Статистика использования типов задач
  @Query("SELECT it.name, COUNT(i) FROM IssueType it LEFT JOIN Issue i ON it.id = i.issueType.id GROUP BY it.id, it.name ORDER BY COUNT(i) DESC")
  List<Object[]> findTypeUsageStatistics();

  // Статистика по проекту
  @Query("SELECT it.name, COUNT(i) FROM IssueType it JOIN Issue i ON it.id = i.issueType.id WHERE i.project.id = :projectId GROUP BY it.id, it.name")
  List<Object[]> findTypeUsageByProjectId(@Param("projectId") Long projectId);

  // МЕТОДЫ ДЛЯ ЧАСТО ИСПОЛЬЗУЕМЫХ ТИПОВ
  // Найти стандартные типы (Bug, Task, Story, Epic)
  @Query("SELECT it FROM IssueType it WHERE LOWER(it.name) IN ('bug', 'task', 'story', 'epic')")
  List<IssueType> findStandardTypes();

  // Найти тип "Bug"
  Optional<IssueType> findBugType();

  // Найти тип "Task"
  Optional<IssueType> findTaskType();

  // Найти тип "Story"
  Optional<IssueType> findStoryType();

  // Найти тип "Epic"
  Optional<IssueType> findEpicType();

  // МЕТОДЫ ДЛЯ ВАЛИДАЦИИ И АДМИНИСТРИРОВАНИЯ
  // Проверить, используется ли тип в задачах
  @Query("SELECT COUNT(i) > 0 FROM Issue i WHERE i.issueType.id = :typeId")
  boolean isTypeUsedInIssues(@Param("typeId") Long typeId);

  // Проверить, используется ли тип в workflow defaults
  @Query("SELECT COUNT(pitwd) > 0 FROM ProjectIssueTypeWorkflowDefault pitwd WHERE pitwd.issueType.id = :typeId")
  boolean isTypeUsedInWorkflowDefaults(@Param("typeId") Long typeId);

  // Безопасное удаление типа
  // @Modifying
  // @Transactional
  // @Query("DELETE FROM IssueType it WHERE it.id = :typeId AND NOT EXISTS (SELECT 1 FROM Issue i WHERE i.issueType.id = :typeId) AND NOT EXISTS (SELECT 1 FROM ProjectIssueTypeWorkflowDefault pitwd WHERE pitwd.issueType.id = :typeId)")
  // int deleteTypeIfNotUsed(@Param("typeId") Long typeId);

  // ПОИСК ПО ВИЗУАЛЬНЫМ АТРИБУТАМ
  // Найти типы с определенной цветовой схемой
  @Query("SELECT it FROM IssueType it WHERE it.colorHexCode LIKE :colorPattern")
  List<IssueType> findByColorPattern(@Param("colorPattern") String colorPattern);

  // ДОПОЛНИТЕЛЬНЫЕ ПОЛЕЗНЫЕ МЕТОДЫ
  // Найти типы по списку ID
  List<IssueType> findByIdIn(List<Long> ids);

  // Найти типы по списку имен
  List<IssueType> findByNameIn(List<String> names);

  // Получить типы с подсчетом задач (для дашборда)
  @Query("SELECT it, COUNT(i) as issueCount FROM IssueType it LEFT JOIN Issue i ON it.id = i.issueType.id GROUP BY it ORDER BY issueCount DESC")
  List<Object[]> findAllWithIssueCount();
}
