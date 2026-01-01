package ru.mymsoft.my_jira.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NonNull;

import java.io.Serializable;

/**
 * Связующая таблица (многие-ко-многим), которая указывает, какие пользователи входят в какие группы.
 */
@Entity
@Table(name = "user_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserGroup.UserGroupId.class)
@Builder
public class UserGroup {
    /**
     * Ссылка на пользователя.
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user;

    /**
     * Ссылка на группу пользователей.
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @NonNull
    private Group group;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UserGroupId implements Serializable {
        private Long user;
        private Long group;
    }
}
