package ru.mymsoft.my_jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Workflow;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    Optional<Workflow> findByName(String name);
    boolean existsByName(String name);
    Page<Workflow> findAllByNameContainingIgnoreCase(String namePart, Pageable pageable);
    List<Workflow> findAllByOrderByNameAsc();
}
