package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.EqualsAndHashCode;

/**
 * Позволяет объединять пользователей в логические группы 
 * (например, "Команда разработки", "Команда QA", "Руководители").
 * Упрощает управление доступом к проектам и массовое назначение прав.
 */
@Entity
@Table(name = "groups",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_groups_name", columnNames = {"name"}),
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {
    /**
     * Уникальный идентификатор группы.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    /**
     * Название группы (например: "Developers", "QA Team", "Admins").
     */
    @Column(name = "name", nullable = false, length = 100)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    /**
     * Описание группы.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    @EqualsAndHashCode.Exclude
    private String description;
}