package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

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
    @NonNull
    private Boolean isDefault;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;
}
