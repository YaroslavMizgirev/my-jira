package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(name = "file_types",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_file_types_extension", columnNames = {"extension"}),
        @UniqueConstraint(name = "uk_file_types_mime_type", columnNames = {"mime_type"}),
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "extension", "mimeType"})
@Builder
public class FileType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "extension", nullable = false, length = 20)
    @NonNull
    private String extension;

    @Column(name = "mime_type", nullable = false, length = 100)
    @NonNull
    private String mimeType;
}
