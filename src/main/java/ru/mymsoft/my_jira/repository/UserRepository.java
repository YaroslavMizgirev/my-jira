package ru.mymsoft.my_jira.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    
    // Поиск по части имени пользователя или email с пагинацией (для автодополнения)
    Page<User> findAllByUsernameContainsIgnoreCase(String username, Pageable pageable);
    Page<User> findAllByEmailContainsIgnoreCase(String email, Pageable pageable);
}