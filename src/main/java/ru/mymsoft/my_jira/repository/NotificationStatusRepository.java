package ru.mymsoft.my_jira.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.NotificationStatus;

@Repository
public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Long> {
    Optional<NotificationStatus> findByName(String name);
    boolean existsByName(String name);
    Page<NotificationStatus> findAllByNameContainingIgnoreCase(String namePart, Pageable pageable);
}
