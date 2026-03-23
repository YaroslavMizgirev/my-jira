package ru.mymsoft.my_jira.model;

import java.time.Instant;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Записывает все значимые изменения и действия, происходящие с задачами
 * (например, изменение статуса, назначение исполнителя, обновление описания,
 * добавление комментария/вложения). Позволяет просматривать историю изменений задачи.
 */
@Entity
@Table(name = "activity_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ActivityLog {
    /**
     * Уникальный идентификатор записи журнала.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Long id;

    /**
     * Ссылка на задачу, к которой относится запись.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "issue_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_activity_log_issue",
            foreignKeyDefinition = "FOREIGN KEY (issue_id) REFERENCES public.issues (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
        )
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Issue issue;

    @EqualsAndHashCode.Include
    private Long getIssueId() {
        return this.issue.getId();
    }

    /**
     * Ссылка на пользователя, который выполнил действие
     * (может быть NULL для системных действий).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        foreignKey = @ForeignKey(
            name = "fk_activity_log_user",
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES public.users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @EqualsAndHashCode.Include
    private Long getUserId() {
        return this.user.getId();
    }

    /**
     * Ссылка на тип действия, которое происходит с задачей.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "action_type_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_activity_log_action_type",
            foreignKeyDefinition = "FOREIGN KEY (action_type_id) REFERENCES public.action_types (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ActionType actionType;

    @EqualsAndHashCode.Include
    private Long getActionTypeId() {
        return this.actionType.getId();
    }

    /**
     * Имя поля, которое было изменено (если применимо).
     */
    @Column(name = "field_name", length = 100)
    @NonNull
    @EqualsAndHashCode.Include
    @ToString.Include
    private String fieldName;

    /**
     * Предыдущее значение поля (если применимо).
     */
    @Column(name = "old_value", columnDefinition = "TEXT")
    @EqualsAndHashCode.Include
    @ToString.Include
    private String oldValue;

    /**
     * Новое значение поля (если применимо).
     */
    @Column(name = "new_value", columnDefinition = "TEXT")
    @EqualsAndHashCode.Include
    @ToString.Include
    private String newValue;

    /**
     * Временная метка создания.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Instant createdAt;
}