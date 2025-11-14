package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.IssueLinkType;

@Repository
public interface IssueLinkTypeRepository extends JpaRepository<IssueLinkType, Long> {

  Optional<IssueLinkType> findByName(String name);
  Optional<IssueLinkType> findByInwardName(String inwardName);
  boolean existsByName(String name);
  boolean existsByInwardName(String inwardName);

  List<IssueLinkType> findByNameContainingIgnoreCase(String namePart);
  List<IssueLinkType> findByInwardNameContainingIgnoreCase(String inwardNamePart);

  List<IssueLinkType> findAllByOrderByNameAsc();
  List<IssueLinkType> findAllByOrderByInwardNameAsc();

  // Поиск по обоим именам одновременно
  @Query("SELECT ilt FROM IssueLinkType ilt WHERE ilt.name LIKE %:searchTerm% OR ilt.inwardName LIKE %:searchTerm%")
  List<IssueLinkType> findByNameOrInwardNameContainingIgnoreCase(@Param("searchTerm") String searchTerm);

  // Получить пары name/inwardName для удобства
  @Query("SELECT ilt.name, ilt.inwardName FROM IssueLinkType ilt ORDER BY ilt.name")
  List<Object[]> findAllNamePairs();

  // Проверить, используется ли тип связи
  @Query("SELECT COUNT(il) > 0 FROM IssueLink il WHERE il.linkType.id = :linkTypeId")
  boolean isLinkTypeUsed(@Param("linkTypeId") Long linkTypeId);

  // Найти взаимные типы связей (если добавишь поле reciprocalType)
  // Optional<IssueLinkType> findByReciprocalType(IssueLinkType reciprocalType);
}
