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
        @UniqueConstraint(name = "uk_file_types_extension_mime_type", columnNames = {"extension", "mime_type"}),
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "extension", nullable = false, length = 20)
    @NonNull
    @EqualsAndHashCode.Include
    private String extension;

    @Column(name = "mime_type", nullable = false, length = 100)
    @NonNull
    @EqualsAndHashCode.Include
    private String mimeType;
}
