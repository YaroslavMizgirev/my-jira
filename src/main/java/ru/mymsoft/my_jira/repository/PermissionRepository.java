package ru.mymsoft.my_jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
    Page<Permission> findAllByNameContainingIgnoreCase(String namePart, Pageable pageable);
    List<Permission> findAllByOrderByNameAsc();
}
