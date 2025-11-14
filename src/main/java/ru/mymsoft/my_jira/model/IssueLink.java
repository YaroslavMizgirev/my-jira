package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "issue_links", uniqueConstraints = {
        @UniqueConstraint(name = "uk_issue_links_unique_pair1", columnNames = { "source_issue_id", "target_issue_id",
                "link_type_id" }),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
@Builder
public class IssueLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_issue_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_issue_links_source_issue",
        foreignKeyDefinition = "FOREIGN KEY (source_issue_id) REFERENCES public.issues (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
        )
    )
    @NonNull
    private Issue sourceIssue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_issue_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_issue_links_target_issue",
        foreignKeyDefinition = "FOREIGN KEY (target_issue_id) REFERENCES public.issues (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
        )
    )
    @NonNull
    private Issue targetIssue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "link_type_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_issue_links_type",
        foreignKeyDefinition = "FOREIGN KEY (link_type_id) REFERENCES public.issue_link_types (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
        )
    )
    @NonNull
    private IssueLinkType linkType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @NonNull
    private Instant createdAt;
}
