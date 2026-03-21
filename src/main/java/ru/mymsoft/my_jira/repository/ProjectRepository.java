package ru.mymsoft.my_jira.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);

    boolean existsByKey(String key);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByKeyAndIdNot(String key, Long id);

    Page<Project> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
