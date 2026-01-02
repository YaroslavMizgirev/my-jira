package ru.mymsoft.my_jira.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mymsoft.my_jira.dto.UserDto;
import ru.mymsoft.my_jira.dto.CreateUserDto;
import ru.mymsoft.my_jira.dto.UpdateUserDto;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto createUser(@NonNull CreateUserDto usr) {
        if (userRepository.existsByEmail(usr.email())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        if (userRepository.existsByUsername(usr.username())) {
            throw new IllegalArgumentException("User with this username already exists");
        }

        User user = Objects.requireNonNull(User.builder()
                .email(usr.email())
                .username(usr.username())
                .passwordHash(passwordEncoder.encode(usr.password()))
                .build(), "User cannot be null");
        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(@NonNull Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByUsername(@NonNull String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByEmail(@NonNull String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> listUsers(String usernameFilter, Pageable pageable) {
        String filter = usernameFilter == null ? "" : usernameFilter;
        return userRepository
                .findAllByUsernameContainsIgnoreCase(filter, pageable)
                .map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<UserDto> listAllUsersSorted(boolean ascending) {
        List<User> users = ascending
            ? userRepository.findAllByOrderByUsernameAsc()
            : userRepository.findAllByOrderByUsernameDesc();
        return users.stream()
            .map(this::toDto)
            .toList();
    }

    @Transactional
    public UserDto updateUser(@NonNull Long userId, @NonNull UpdateUserDto updatedUser) {
        // Проверяем соответствие ID
        if (!userId.equals(updatedUser.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }

        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Находим обновляемого пользователя
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Проверяем права доступа: только сам пользователь может обновлять свои данные
        if (!existingUser.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        // Проверяем текущий пароль
        if (!passwordEncoder.matches(updatedUser.currentPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Обновляем email, если предоставлен
        if (updatedUser.email() != null && !updatedUser.email().trim().isEmpty()) {
            // Проверяем уникальность
            if (!updatedUser.email().equals(existingUser.getEmail()) && 
                userRepository.existsByEmailAndIdNot(updatedUser.email(), updatedUser.id())) {
                    throw new IllegalArgumentException("User with this email already exists");
            }
            existingUser.setEmail(updatedUser.email());
        }
        
        // Обновляем username, если предоставлен
        if (updatedUser.username() != null && !updatedUser.username().trim().isEmpty()) {
            if (!updatedUser.username().equals(existingUser.getUsername()) && 
                userRepository.existsByUsernameAndIdNot(updatedUser.username(), updatedUser.id())) {
                    throw new IllegalArgumentException("User with this username already exists");
            }
            existingUser.setUsername(updatedUser.username());
        }
            
        // Пароль обязателен при полном обновлении
        if (updatedUser.newPassword() != null && !updatedUser.newPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.newPassword()));
        }

        User savedUser = userRepository.save(existingUser);
        return toDto(savedUser);
    }
    
    @Transactional
    public void deleteUser(@NonNull Long id) {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Находим пользователя для удаления
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        // Проверяем права доступа: только сам пользователь может удалить свой аккаунт
        // (или админ, но админская логика должна быть в отдельном сервисе)
        if (!existingUser.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You can only delete your own account");
        }
        
        userRepository.delete(existingUser);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }
}