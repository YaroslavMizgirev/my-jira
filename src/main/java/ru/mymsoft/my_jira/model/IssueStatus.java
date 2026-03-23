package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issue_statuses",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_issue_statuses_name", columnNames = {"name"})
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IssueStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @EqualsAndHashCode.Exclude
    private String description;
}