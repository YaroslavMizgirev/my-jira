
package ru.mymsoft.my_jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.ActionType;

@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Long> {

  Optional<ActionType> findByName(String name);
  boolean existsByName(String name);

  List<ActionType> findByNameContainingIgnoreCase(String namePart);
  List<ActionType> findByNameStartingWith(String prefix);

  List<ActionType> findAllByOrderByNameAsc();
  List<ActionType> findAllByOrderByNameDesc();
}
