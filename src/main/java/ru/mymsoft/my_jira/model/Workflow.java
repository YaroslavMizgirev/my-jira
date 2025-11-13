package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "workflows",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_workflows_name", columnNames = {"name"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
@Builder
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    // Связь с WorkflowStatus
    @ManyToMany
    @JoinTable(
            name = "workflow_statuses",
            joinColumns = @JoinColumn(name = "workflow_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private Set<IssueStatus> statuses = new HashSet<>();

    // Связь с WorkflowTransition
    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkflowTransition> transitions = new HashSet<>();
}
