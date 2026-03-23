package ru.mymsoft.my_jira.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mymsoft.my_jira.dto.UserDto;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.repository.UserRepository;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management API")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user profile")
    public ResponseEntity<UserDto> getCurrentUser(OAuth2AuthenticationToken authentication) {
        String provider = authentication.getAuthorizedClientRegistrationId();
        String oauthId = authentication.getName();

        User user = userRepository.findByOauthProviderAndOauthId(provider, oauthId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "oauthId", oauthId));

        return ResponseEntity.ok(toDto(user));
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                user.getOauthProvider()
        );
    }
}
