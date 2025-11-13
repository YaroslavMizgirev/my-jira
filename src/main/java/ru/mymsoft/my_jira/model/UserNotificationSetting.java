package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notification_settings",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_notification_settings_unique", columnNames = {"user_id", "notification_type"})
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class UserNotificationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "notification_type", nullable = false, length = 50)
    private String notificationType;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
}
