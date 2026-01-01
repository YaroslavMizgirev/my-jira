package ru.mymsoft.my_jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByName(String name);
  boolean existsByNameAndIsSystemGroup(String name, boolean isSystemGroup);

  // Поиск по части имени (для автодополнения)
  Page<Group> findAllByNameContainsIgnoreCase(String namePart, Pageable pageable);

  // Получить все группы с сортировкой
  List<Group> findAllByOrderByNameAsc();

  // Найти системные и несистемные (пользовательские) группы
  List<Group> findAllByIsSystemGroupTrueOrderByNameAsc();
  List<Group> findAllByIsSystemGroupFalseOrderByNameAsc();

  // Поиск групп по описанию
  List<Group> findAllByDescriptionContainsIgnoreCase(String descriptionPart);

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
