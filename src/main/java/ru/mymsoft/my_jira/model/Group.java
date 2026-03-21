package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Builder;
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
@ToString
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
    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "must not exceed 100 characters")
    private String name;

    /**
     * Описание группы.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Флаг для системных/встроенных групп.
     */
    @Column(name = "is_system_group", nullable = false)
    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean isSystemGroup = false;

    public Boolean isSystemGroup() {
        return isSystemGroup;
    }

    public void setSystemGroup(Boolean isSystemGroup) {
        this.isSystemGroup = isSystemGroup;
    }
}
