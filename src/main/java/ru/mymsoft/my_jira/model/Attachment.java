package ru.mymsoft.my_jira.model;

import java.time.Instant;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString(onlyExplicitlyIncluded = true)
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "issue_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_attachments_issue")
    )
    @ToString.Exclude
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uploader_id", nullable = false, foreignKey = @ForeignKey(name = "fk_attachments_uploader")
    )
    @ToString.Exclude
    private User uploader;

    @Column(name = "file_name", nullable = false, length = 255)
    @ToString.Include
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_attachments_file_type")
    )
    @ToString.Exclude
    private FileType fileType;

    @Column(name = "file_size_bytes", nullable = false)
    @ToString.Exclude
    private Long fileSizeBytes;

    @Column(name = "storage_path", nullable = false, length = 4096)
    @ToString.Include
    private String storagePath;

    @Column(name = "description", columnDefinition = "text")
    @ToString.Include
    private String description;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    @ToString.Include
    private Instant updatedAt;
}
