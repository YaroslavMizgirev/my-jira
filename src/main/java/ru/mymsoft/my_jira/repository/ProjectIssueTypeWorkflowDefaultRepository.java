package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.ProjectIssueTypeWorkflowDefault;
import ru.mymsoft.my_jira.model.ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId;

@Repository
public interface ProjectIssueTypeWorkflowDefaultRepository extends JpaRepository<ProjectIssueTypeWorkflowDefault, ProjectIssueTypeWorkflowDefaultId> {

}
