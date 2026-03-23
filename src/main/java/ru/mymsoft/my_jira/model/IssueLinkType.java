package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "issue_link_types",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_issue_link_types_name", columnNames = {"name"}),
        @UniqueConstraint(name = "uk_issue_link_types_inward_name", columnNames = {"inward_name"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class IssueLinkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "inward_name", nullable = false, length = 100)
    @NonNull
    @EqualsAndHashCode.Include
    private String inwardName;

    @Column(name = "description", columnDefinition = "TEXT")
    @EqualsAndHashCode.Exclude
    private String description;
}