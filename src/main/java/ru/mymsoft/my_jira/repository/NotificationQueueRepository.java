package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.NotificationQueue;

@Repository
public interface NotificationQueueRepository extends JpaRepository<NotificationQueue, Long> {

}
