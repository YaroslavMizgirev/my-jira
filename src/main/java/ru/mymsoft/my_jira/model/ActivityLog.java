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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "activity_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_activity_log_issue",
        foreignKeyDefinition = "FOREIGN KEY (issue_id) REFERENCES public.issues (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
        )
    )
    @NonNull
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
        name = "fk_activity_log_user",
        foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES public.users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "action_type_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_activity_log_action_type",
        foreignKeyDefinition = "FOREIGN KEY (action_type_id) REFERENCES public.action_types (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    private ActionType actionType;

    @Column(name = "field_name", length = 100)
    private String fieldName;

    @Lob
    @Column(name = "old_value")
    private String oldValue;

    @Lob
    @Column(name = "new_value")
    private String newValue;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull
    private Instant createdAt;
}
