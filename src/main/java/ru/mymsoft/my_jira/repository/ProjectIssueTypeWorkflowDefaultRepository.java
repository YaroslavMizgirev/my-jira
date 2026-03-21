package ru.mymsoft.my_jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.ProjectIssueTypeWorkflowDefault;
import ru.mymsoft.my_jira.model.ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId;

@Repository
public interface ProjectIssueTypeWorkflowDefaultRepository extends JpaRepository<ProjectIssueTypeWorkflowDefault, ProjectIssueTypeWorkflowDefaultId> {

    List<ProjectIssueTypeWorkflowDefault> findAllByProject_Id(Long projectId);

    boolean existsByProject_IdAndIssueType_IdAndWorkflow_Id(Long projectId, Long issueTypeId, Long workflowId);

    @Modifying
    @Query("DELETE FROM ProjectIssueTypeWorkflowDefault d WHERE d.project.id = :projectId AND d.issueType.id = :issueTypeId AND d.workflow.id = :workflowId")
    void deleteByProjectIdAndIssueTypeIdAndWorkflowId(@Param("projectId") Long projectId,
                                                       @Param("issueTypeId") Long issueTypeId,
                                                       @Param("workflowId") Long workflowId);
}
