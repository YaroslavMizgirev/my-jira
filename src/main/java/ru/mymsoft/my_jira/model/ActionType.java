package ru.mymsoft.my_jira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Хранит все типы действий, происходящие с задачами 
 * (например, изменение статуса, назначение исполнителя, 
 * обновление описания, добавление комментария/вложения).
 */
@Entity
@Table(name = "action_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ActionType {
  /**
   * Уникальный идентификатор типа действия.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Тип действия (например, "STATUS_CHANGE", "ASSIGNED", "COMMENTED").
   */
  @Column(name = "name", nullable = false, unique = true)
  @NonNull
  private String name;
}
