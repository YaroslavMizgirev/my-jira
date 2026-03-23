package ru.mymsoft.my_jira.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;
import lombok.*;

@Entity
@Table(name = "workflow_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class WorkflowStatus {
    @EmbeddedId
    @ToString.Exclude
    private WorkflowStatusId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("workflowId")
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_statuses_workflow",
        foreignKeyDefinition = "FOREIGN KEY (workflow_id) REFERENCES public.workflows (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT")
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("statusId")
    @JoinColumn(name = "status_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_statuses_status",
        foreignKeyDefinition = "FOREIGN KEY (status_id) REFERENCES public.issue_statuses (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"))
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private IssueStatus issueStatus;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @ToString(onlyExplicitlyIncluded = true)
    public static class WorkflowStatusId implements Serializable {
        @Column(name = "workflow_id")
        @EqualsAndHashCode.Include
        private Long workflowId;
        @Column(name = "status_id")
        @EqualsAndHashCode.Include
        private Long statusId;
    }
}