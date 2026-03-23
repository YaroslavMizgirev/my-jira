package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "issue_types",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_issue_types_name", columnNames = {"name"}),
    })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class IssueType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @NonNull
    @EqualsAndHashCode.Include
    @ToString.Include
    private String name;

    @Column(name = "icon_url")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String iconUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String description;
}