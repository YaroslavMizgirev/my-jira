package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workflow_transitions",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_workflow_transitions_unique_pair",
            columnNames = {
                "workflow_id",
                "name",
                "from_status_id",
                "to_status_id"
            }
        ),
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class WorkflowTransition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Long id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_status_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_transitions_from_status",
        foreignKeyDefinition = "FOREIGN KEY (from_status_id) REFERENCES public.issue_statuses (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT")
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private IssueStatus fromStatus;

    @EqualsAndHashCode.Include
    private Long getFromStatusId() {
        return this.fromStatus.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_status_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_transitions_to_status",
        foreignKeyDefinition = "FOREIGN KEY (to_status_id) REFERENCES public.issue_statuses (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT")
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private IssueStatus toStatus;

    @EqualsAndHashCode.Include
    private Long getToStatusId() {
        return this.toStatus.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_transitions_workflow",
        foreignKeyDefinition = "FOREIGN KEY (workflow_id) REFERENCES public.workflows (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT")
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Workflow workflow;

    @EqualsAndHashCode.Include
    private Long getWorkflowId() {
        return this.workflow.getId();
    }
}