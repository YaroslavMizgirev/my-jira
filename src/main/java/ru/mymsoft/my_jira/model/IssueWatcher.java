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
    @JoinColumn(name = "issue_id", nullable = false, foreignKey = @ForeignKey(name = "fk_issue_watchers_issue")
    )
    @NonNull
    private Issue issue;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_issue_watchers_user")
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