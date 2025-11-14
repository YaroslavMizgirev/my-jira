package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(name = "groups",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_groups_name", columnNames = {"name"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ={"id", "name", "isSystemGroup"})
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NonNull
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "is_system_group", nullable = false)
    @NonNull
    private Boolean isSystemGroup = false;
}
