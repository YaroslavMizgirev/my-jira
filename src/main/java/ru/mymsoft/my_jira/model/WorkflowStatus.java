package ru.mymsoft.my_jira.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workflow_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WorkflowStatus.WorkflowStatusId.class)
@Builder
public class WorkflowStatus {
  @Id
  @Column(name = "workflow_id", nullable = false)
  private Long workflowId;

  @Id
  @Column(name = "status_id", nullable = false)
  private Long statusId;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class WorkflowStatusId implements Serializable {
    private Long workflowId;
    private Long statusId;
  }
}
