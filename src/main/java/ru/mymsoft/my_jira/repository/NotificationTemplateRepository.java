package ru.mymsoft.my_jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.NotificationTemplate;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    Optional<NotificationTemplate> findByName(String name);
    boolean existsByName(String name);
    Page<NotificationTemplate> findAllByNameContainingIgnoreCase(String namePart, Pageable pageable);
    List<NotificationTemplate> findAllByOrderByNameAsc();
    List<NotificationTemplate> findAllByIsActiveTrue();
}
