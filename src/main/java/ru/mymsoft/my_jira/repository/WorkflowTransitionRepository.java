package ru.mymsoft.my_jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.WorkflowTransition;

@Repository
public interface WorkflowTransitionRepository extends JpaRepository<WorkflowTransition, Long> {
    List<WorkflowTransition> findAllByWorkflowId(Long workflowId);
    boolean existsByWorkflowIdAndName(Long workflowId, String name);
    boolean existsByWorkflowIdAndFromStatusIdAndToStatusId(Long workflowId, Long fromStatusId, Long toStatusId);
}
