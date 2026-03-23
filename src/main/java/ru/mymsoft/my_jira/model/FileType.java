package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@ToString
@Builder
public class FileType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "extension", nullable = false, length = 20)
    @NotBlank(message = "must not be blank")
    @Size(max = 20, message = "must not exceed 20 characters")
    private String extension;

    @Column(name = "mime_type", nullable = false, length = 100)
    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "must not exceed 100 characters")
    private String mimeType;
}
