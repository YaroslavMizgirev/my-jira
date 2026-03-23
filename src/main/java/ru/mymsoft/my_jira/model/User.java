package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

/**
 * Хранит информацию обо всех зарегистрированных пользователях системы.
 * Аутентификация — только через OAuth2 (Google, GitHub, GitLab, Yandex, Mail.ru).
 */
@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uk_users_username", columnNames = {"username"}),
        @UniqueConstraint(name = "uk_users_oauth", columnNames = {"oauth_provider", "oauth_id"}),
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Long id;

    @Column(name = "email", nullable = false)
    @NonNull
    @EqualsAndHashCode.Include
    @ToString.Include
    private String email;

    @Column(name = "username", nullable = false)
    @NonNull
    @EqualsAndHashCode.Include
    @ToString.Include
    private String username;

    /**
     * Хэш пароля. Nullable — используется только для OAuth2-аутентификации.
     */
    @Column(name = "password_hash")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String passwordHash;

    /**
     * Провайдер OAuth2: google, github, gitlab, yandex, mailru.
     */
    @Column(name = "oauth_provider", length = 50)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private String oauthProvider;

    /**
     * Уникальный идентификатор пользователя у провайдера OAuth2.
     */
    @Column(name = "oauth_id", length = 255)
    @EqualsAndHashCode.Exclude
    private String oauthId;

    /**
     * Отображаемое имя (из профиля OAuth2-провайдера).
     */
    @Column(name = "display_name", length = 255)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private String displayName;

    /**
     * URL аватара пользователя (из профиля OAuth2-провайдера).
     */
    @Column(name = "avatar_url", length = 2048)
    @EqualsAndHashCode.Exclude
    private String avatarUrl;
}
