package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "issue_watchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(IssueWatcher.IssueWatcherId.class)
@Builder
public class IssueWatcher {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_issue_watchers_issue",
        foreignKeyDefinition = "FOREIGN KEY (issue_id) REFERENCES public.issues (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
        )
    )
    @NonNull
    private Issue issue;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_issue_watchers_user",
        foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES public.users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    private User user;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class IssueWatcherId implements Serializable {
        private Long issue;
        private Long user;
    }
}