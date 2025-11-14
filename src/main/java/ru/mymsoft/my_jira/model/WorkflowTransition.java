package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workflow_transitions",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_workflow_transitions_unique_pair1", columnNames = {"workflow_id", "name"}),
        @UniqueConstraint(name = "uk_workflow_transitions_unique_pair2", columnNames = {"workflow_id", "from_status_id", "to_status_id"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class WorkflowTransition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_status_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_transitions_from_status",
        foreignKeyDefinition = "FOREIGN KEY (from_status_id) REFERENCES public.issue_statuses (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    private IssueStatus fromStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_status_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_transitions_to_status",
        foreignKeyDefinition = "FOREIGN KEY (to_status_id) REFERENCES public.issue_statuses (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    private IssueStatus toStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_workflow_transitions_workflow",
        foreignKeyDefinition = "FOREIGN KEY (workflow_id) REFERENCES public.workflows (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    private Workflow workflow;
}
