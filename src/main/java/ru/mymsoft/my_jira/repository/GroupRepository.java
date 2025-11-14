package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByName(String name);

  List<Group> findByIsSystemGroup(Boolean isSystemGroup);

  boolean existsByName(String name);

  // Поиск по части имени (для автодополнения)
  List<Group> findByNameContainingIgnoreCase(String namePart);

  // Получить все группы с сортировкой
  List<Group> findAllByOrderByNameAsc();

  // Найти системные группы (часто используемый случай)
  List<Group> findByIsSystemGroupTrueOrderByNameAsc();

  // Найти несистемные (пользовательские) группы
  List<Group> findByIsSystemGroupFalseOrderByNameAsc();

  // Поиск групп по описанию
  List<Group> findByDescriptionContainingIgnoreCase(String descriptionPart);

  // Статистика по группам
  @Query("SELECT COUNT(g) FROM Group g WHERE g.isSystemGroup = :isSystem")
  long countBySystemStatus(@Param("isSystem") boolean isSystem);

  // Проверить, используется ли группа в project members
  @Query("SELECT COUNT(pm) > 0 FROM ProjectMember pm WHERE pm.group.id = :groupId")
  boolean isGroupUsedInProjects(@Param("groupId") Long groupId);

  // Найти группы по количеству пользователей (если добавишь связь)
  // @Query("SELECT g FROM Group g LEFT JOIN g.users u GROUP BY g HAVING COUNT(u)
  // > :minUsers")
  // List<Group> findGroupsWithMinimumUsers(@Param("minUsers") long minUsers);
}
