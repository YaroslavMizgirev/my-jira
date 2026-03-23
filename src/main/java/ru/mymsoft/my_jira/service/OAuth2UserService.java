package ru.mymsoft.my_jira.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.repository.UserRepository;

import java.util.Map;

/**
 * Обрабатывает OAuth2 логин: извлекает данные из провайдера,
 * создаёт нового пользователя или обновляет существующего.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String oauthId = extractOauthId(provider, attributes);
        String email = extractEmail(provider, attributes);
        String displayName = extractDisplayName(provider, attributes);
        String avatarUrl = extractAvatarUrl(provider, attributes);

        if (email == null || email.isBlank()) {
            log.warn("OAuth2 provider '{}' did not return an email for user {}", provider, oauthId);
            email = provider + "_" + oauthId + "@noemail.local";
        }

        User user = userRepository.findByOauthProviderAndOauthId(provider, oauthId)
                .orElseGet(() -> createNewUser(provider, oauthId, email));

        updateUserFromOAuth2(user, email, displayName, avatarUrl);
        userRepository.save(user);

        log.info("OAuth2 login: provider={}, userId={}, email={}", provider, user.getId(), user.getEmail());
        return oAuth2User;
    }

    private User createNewUser(String provider, String oauthId, String email) {
        String username = generateUsername(email, provider, oauthId);
        return User.builder()
                .oauthProvider(provider)
                .oauthId(oauthId)
                .email(email)
                .username(username)
                .build();
    }

    private void updateUserFromOAuth2(User user, String email, String displayName, String avatarUrl) {
        if (!user.getEmail().equals(email)) {
            user.setEmail(email);
        }
        user.setDisplayName(displayName);
        user.setAvatarUrl(avatarUrl);
    }

    private String generateUsername(String email, String provider, String oauthId) {
        String base = email.split("@")[0].replaceAll("[^a-zA-Z0-9_]", "_");
        if (base.length() > 50) {
            base = base.substring(0, 50);
        }
        String candidate = base;
        int suffix = 1;
        while (userRepository.existsByUsername(candidate)) {
            candidate = base + "_" + suffix++;
        }
        return candidate;
    }

    // --- Attribute extractors per provider ---

    private String extractOauthId(String provider, Map<String, Object> attrs) {
        return switch (provider) {
            case "github" -> String.valueOf(attrs.get("id"));
            case "google" -> String.valueOf(attrs.get("sub"));
            case "gitlab" -> String.valueOf(attrs.get("id"));
            case "yandex" -> String.valueOf(attrs.get("id"));
            case "mailru" -> String.valueOf(attrs.get("id"));
            default -> String.valueOf(attrs.getOrDefault("id", attrs.get("sub")));
        };
    }

    private String extractEmail(String provider, Map<String, Object> attrs) {
        return switch (provider) {
            case "yandex" -> (String) attrs.get("default_email");
            case "mailru" -> (String) attrs.get("email");
            default -> (String) attrs.get("email");
        };
    }

    private String extractDisplayName(String provider, Map<String, Object> attrs) {
        return switch (provider) {
            case "github" -> (String) attrs.getOrDefault("name", attrs.get("login"));
            case "google" -> (String) attrs.get("name");
            case "gitlab" -> (String) attrs.get("name");
            case "yandex" -> (String) attrs.get("real_name");
            case "mailru" -> attrs.get("first_name") + " " + attrs.get("last_name");
            default -> (String) attrs.getOrDefault("name", "User");
        };
    }

    private String extractAvatarUrl(String provider, Map<String, Object> attrs) {
        return switch (provider) {
            case "github" -> (String) attrs.get("avatar_url");
            case "google" -> (String) attrs.get("picture");
            case "gitlab" -> (String) attrs.get("avatar_url");
            case "yandex" -> null;
            case "mailru" -> (String) attrs.get("image");
            default -> null;
        };
    }
}
