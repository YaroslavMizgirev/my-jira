package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "priorities",
    uniqueConstraints = {
            @UniqueConstraint(name = "uk_priorities_level", columnNames = {"level"}),
            @UniqueConstraint(name = "uk_priorities_name", columnNames = {"name"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "level"})
@Builder
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "color_hex_code", length = 7)
    private String colorHexCode;
}
