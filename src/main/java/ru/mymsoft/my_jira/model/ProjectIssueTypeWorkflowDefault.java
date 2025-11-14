package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "project_issue_type_workflow_defaults")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProjectIssueTypeWorkflowDefault.ProjectIssueTypeWorkflowDefaultId.class)
@Builder
public class ProjectIssueTypeWorkflowDefault {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @NonNull
    private Project project;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_type_id", nullable = false)
    @NonNull
    private IssueType issueType;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false)
    @NonNull
    private Workflow workflow;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ProjectIssueTypeWorkflowDefaultId implements Serializable {
        private Long project;
        private Long issueType;
        private Long workflow;
    }
}
