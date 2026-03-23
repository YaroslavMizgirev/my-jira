package ru.mymsoft.my_jira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mymsoft.my_jira.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
