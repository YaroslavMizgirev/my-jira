package ru.mymsoft.my_jira.model;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "issues",
    uniqueConstraints = {
        @UniqueConstraint(name ="uk_issues_key", columnNames = {"key"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "key"})
@ToString
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "key", nullable = false)
    @NotBlank(message = "must not be blank")
    private String key;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "must not be blank")
    private String title;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", foreignKey = @ForeignKey(name = "fk_issues_assignee")
    )
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_type_id", foreignKey = @ForeignKey(name = "fk_issues_issue_type")
    )
    private IssueType issueType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id", foreignKey = @ForeignKey(name = "fk_issues_priority")
    )
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_id", nullable = false, foreignKey = @ForeignKey(name = "fk_issues_reporter")
    )
    @NotNull(message = "must not be null")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "fk_issues_status")
    )
    private IssueStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", foreignKey = @ForeignKey(name = "fk_issues_workflow")
    )
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_issues_project")
    )
    @NotNull(message = "must not be null")
    private Project project;
}
