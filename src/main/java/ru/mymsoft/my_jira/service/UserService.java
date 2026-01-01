package ru.mymsoft.my_jira.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public UserDto createUser(CreateUserDto usr) {
        if (userRepository.existsByEmail(usr.email())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        if (userRepository.existsByUsername(usr.username())) {
            throw new IllegalArgumentException("User with this username already exists");
        }

        User user = User.builder()
                .email(usr.email())
                .username(usr.username())
                .passwordHash(passwordEncoder.encode(usr.password()))
                .build();

        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> listUsers(String usernameFilter, Pageable pageable) {
        String filter = usernameFilter == null ? "" : usernameFilter;
        return userRepository
                .findAllByUsernameContainsIgnoreCase(filter, pageable)
                .map(this::toDto);
    }

    @Transactional
    public UserDto updateUser(UpdateUserDto updatedUser) {
        User existingUser = userRepository.findById(updatedUser.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + updatedUser.id()));
        existingUser.setEmail(updatedUser.email());
        existingUser.setUsername(updatedUser.username());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.newPassword()));

        User savedUser = userRepository.save(existingUser);
        return toDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        userRepository.deleteById(userId);
    }
    
    @Transactional
    public void deleteUser(UserDto userDto) {
        deleteUser(userDto.id());
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername()
        );
    }
}