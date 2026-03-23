package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

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

    @Column(name = "password_hash")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private String passwordHash;

    @Column(name = "oauth_provider", length = 50)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private String oauthProvider;

    @Column(name = "oauth_id", length = 255)
    @EqualsAndHashCode.Exclude
    private String oauthId;

    @Column(name = "display_name", length = 255)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private String displayName;

    @Column(name = "avatar_url", length = 2048)
    @EqualsAndHashCode.Exclude
    private String avatarUrl;
}
