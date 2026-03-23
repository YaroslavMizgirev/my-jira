package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Хранит все типы действий, происходящие с задачами
 * (например: изменение статуса, назначение исполнителя,
 * обновление описания, добавление комментария/вложения).
 */
@Entity
@Table(name = "action_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ActionType {
    /**
     * Уникальный идентификатор типа действия.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    /**
     * Тип действия (например, "STATUS_CHANGE", "ASSIGNED", "COMMENTED").
     */
    @Column(name = "name", nullable = false, unique = true, length = 100)
    @EqualsAndHashCode.Include
    private String name;

    @PrePersist
    @PreUpdate
    private void validateName() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public static class ActionTypeBuilder {
        public ActionTypeBuilder name(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            this.name = name.trim();
            return this;
        }
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }
}