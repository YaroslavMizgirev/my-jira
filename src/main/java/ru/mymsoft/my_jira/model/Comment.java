package ru.mymsoft.my_jira.model;

import java.time.Instant;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_comments_issue",
        foreignKeyDefinition = "FOREIGN KEY (issue_id) REFERENCES public.issues (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
    )
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Issue issue;

    @EqualsAndHashCode.Include
    private Long getIssueId() {
        return this.issue.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_comments_author",
        foreignKeyDefinition = "FOREIGN KEY (author_id) REFERENCES public.users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
    )
    )
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User author;

    @EqualsAndHashCode.Include
    private Long getAuthorId() {
        return this.author.getId();
    }

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @NonNull
    @EqualsAndHashCode.Include
    @ToString.Include
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Instant updatedAt;
}
