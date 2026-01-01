package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Builder;
import lombok.NonNull;
import lombok.AccessLevel;

/**
 * Хранит информацию обо всех зарегистрированных пользователях системы.
 * Это центральная сущность, к которой привязаны многие другие действия и объекты.
 */
@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uk_users_username", columnNames = {"username"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ={"id", "email"})
@ToString
@Builder
public class User {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Электронная почта пользователя (используется для входа и уведомлений).
     */
    @Column(name = "email", nullable = false)
    @NonNull
    private String email;

    /**
     * Уникальное имя пользователя.
     */
    @Column(name = "username", nullable = false)
    @NonNull
    private String username;

    /**
     * Хэш пароля пользователя.
     * SHA-256 или bcrypt (рекомендуется для безопасности).
     */
    @Column(name = "password_hash", nullable = false)
    @ToString.Exclude
    @NonNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String passwordHash;

    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPassword() {
        return passwordHash;
    }
}
