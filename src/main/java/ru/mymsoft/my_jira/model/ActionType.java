package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Хранит все типы действий, происходящие с задачами (например, изменение статуса, назначение исполнителя, обновление описания, добавление комментария/вложения).
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
    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    @EqualsAndHashCode.Include
    private String name;
}