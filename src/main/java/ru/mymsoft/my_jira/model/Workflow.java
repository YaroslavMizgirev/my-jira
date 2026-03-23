package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workflows",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_workflows_name", columnNames = {"name"}),
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", nullable = false)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}