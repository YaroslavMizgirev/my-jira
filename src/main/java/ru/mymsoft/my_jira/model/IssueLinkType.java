package ru.mymsoft.my_jira.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "issue_link_types", uniqueConstraints = {
        @UniqueConstraint(name = "uk_issue_link_types_name", columnNames = { "name" }),
        @UniqueConstraint(name = "uk_issue_link_types_inward_name", columnNames = { "inward_name" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class IssueLinkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "must not exceed 100 characters")
    private String name;

    @Column(name = "inward_name", nullable = false, length = 100)
    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "must not exceed 100 characters")
    private String inwardName;
}
