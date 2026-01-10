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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "issue_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_attachments_issue",
            foreignKeyDefinition = "FOREIGN KEY (issue_id) REFERENCES public.issues (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE"
        )
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uploader_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_attachments_uploader",
        foreignKeyDefinition = "FOREIGN KEY (uploader_id) REFERENCES public.users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
    )
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User uploader;

    @Column(name = "file_name", nullable = false, length = 255)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_type_id", nullable = false, foreignKey = @ForeignKey(
        name = "fk_attachments_file_type",
        foreignKeyDefinition = "FOREIGN KEY (file_type_id) REFERENCES public.file_types (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE RESTRICT"
    )
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private FileType fileType;

    @EqualsAndHashCode.Include
    private Long getFileTypeId() {
        return this.fileType.getId();
    }

    @Column(name = "file_size_bytes", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long fileSizeBytes;

    @Column(name = "storage_path", nullable = false, length = 4096)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String storagePath;

    @Column(name = "description", columnDefinition = "text")
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private String description;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Include
    private Instant updatedAt;
}