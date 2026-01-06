package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "projects",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_projects_key", columnNames = {"key"}),
        @UniqueConstraint(name = "uk_projects_name", columnNames = {"name"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "key"})
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "key", nullable = false, length = 50)
    @NonNull
    private String key;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lead_id", nullable = false)
    @NonNull
    private User lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_workflow_id")
    private Workflow defaultWorkflow;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @NonNull
    private Instant updatedAt;
}
