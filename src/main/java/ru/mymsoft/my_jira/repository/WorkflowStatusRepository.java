package ru.mymsoft.my_jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.WorkflowStatus;
import ru.mymsoft.my_jira.model.WorkflowStatus.WorkflowStatusId;

@Repository
public interface WorkflowStatusRepository extends JpaRepository<WorkflowStatus, WorkflowStatusId> {
    List<WorkflowStatus> findAllByIdWorkflowId(Long workflowId);
    boolean existsByIdWorkflowIdAndIdStatusId(Long workflowId, Long statusId);
}
