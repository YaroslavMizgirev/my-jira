package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.AccessLevel;

/**
 * Позволяет объединять пользователей в логические группы 
 * (например, "Команда разработки", "Команда QA", "Руководители").
 * Упрощает управление доступом к проектам и массовое назначение прав.
 */
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
    /**
     * Уникальный идентификатор группы.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название группы (например: "Developers", "QA Team", "Admins").
     */
    @Column(name = "name", nullable = false, length = 100)
    @NonNull
    private String name;

    /**
     * Описание группы.
     */
    @Lob
    @Column(name = "description")
    private String description;

    /**
     * Флаг для системных/встроенных групп.
     */
    @Column(name = "is_system_group", nullable = false)
    @Builder.Default
    @NonNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean isSystemGroup = false;

    public boolean isSystemGroup() {
        return Boolean.TRUE.equals(isSystemGroup);
    }

    public void setSystemGroup(Boolean isSystemGroup) {
        this.isSystemGroup = isSystemGroup;
    }
}