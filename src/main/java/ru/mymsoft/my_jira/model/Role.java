package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_roles_name", columnNames = {"name"}),
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @EqualsAndHashCode.Exclude
    private String description;
}