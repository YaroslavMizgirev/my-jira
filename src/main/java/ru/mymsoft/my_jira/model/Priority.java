package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "priorities",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_priorities_level", columnNames = {"level"}),
        @UniqueConstraint(name = "uk_priorities_name", columnNames = {"name"}),
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "level", nullable = false)
    @NonNull
    @EqualsAndHashCode.Include
    private Integer level;

    @Column(name = "name", nullable = false, length = 50)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "icon_url")
    @EqualsAndHashCode.Exclude
    private String iconUrl;

    @Column(name = "color_hex_code", length = 7)
    @EqualsAndHashCode.Exclude
    private String colorHexCode;
}
