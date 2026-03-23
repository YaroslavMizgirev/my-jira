package ru.mymsoft.my_jira.controller.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.repository.UserRepository;
import ru.mymsoft.my_jira.service.IssueService;
import ru.mymsoft.my_jira.service.ProjectService;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ProjectService projectService;
    private final IssueService issueService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        User currentUser = resolveUser(oAuth2User);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("projects",
                projectService.list("", PageRequest.of(0, 8)).getContent());
        model.addAttribute("recentIssues",
                issueService.list("", PageRequest.of(0, 10)).getContent());
        return "dashboard";
    }

    private User resolveUser(OAuth2User oAuth2User) {
        if (oAuth2User == null) return null;
        String email = extractEmail(oAuth2User);
        if (email == null) return null;
        return userRepository.findByEmail(email).orElse(null);
    }

    private String extractEmail(OAuth2User oAuth2User) {
        Object email = oAuth2User.getAttributes().get("email");
        if (email != null) return email.toString();
        Object defaultEmail = oAuth2User.getAttributes().get("default_email"); // Yandex
        if (defaultEmail != null) return defaultEmail.toString();
        return null;
    }
}
