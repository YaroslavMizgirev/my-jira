package ru.mymsoft.my_jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    Optional<Priority> findByName(String name);
    boolean existsByName(String name);
    boolean existsByLevel(Integer level);
    Page<Priority> findAllByNameContainingIgnoreCase(String namePart, Pageable pageable);
    List<Priority> findAllByOrderByLevelAsc();
}
