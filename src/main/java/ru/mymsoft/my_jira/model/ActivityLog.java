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
@ToString(onlyExplicitlyIncluded = true)
public class ActivityLog {
    /**
     * Уникальный идентификатор записи журнала.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    /**
     * Ссылка на задачу, к которой относится запись.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false, foreignKey = @ForeignKey(name = "fk_activity_log_issue")
    )
    @NonNull
    @ToString.Exclude
    private Issue issue;

    /**
     * Ссылка на пользователя, который выполнил действие
     * (может быть NULL для системных действий).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_activity_log_user")
    )
    @ToString.Exclude
    private User user;

    /**
     * Ссылка на тип действия, которое происходит с задачей.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "action_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_activity_log_action_type")
    )
    @NonNull
    @ToString.Exclude
    private ActionType actionType;

    /**
     * Имя поля, которое было изменено (если применимо).
     */
    @Column(name = "field_name", length = 100)
    @ToString.Include
    private String fieldName;

    /**
     * Предыдущее значение поля (если применимо).
     */
    @Column(name = "old_value", columnDefinition = "TEXT")
    @ToString.Exclude
    private String oldValue;

    /**
     * Новое значение поля (если применимо).
     */
    @Column(name = "new_value", columnDefinition = "TEXT")
    @ToString.Exclude
    private String newValue;

    /**
     * Временная метка создания.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull
    @ToString.Include
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityLog that = (ActivityLog) o;
        if (this.id == null) return false;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }
}
