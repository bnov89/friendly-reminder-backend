package com.archyle.fra.friendlyreminderbackend.output.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo_items")
public class TodoItemEntity {
  @Id @GeneratedValue private Long id;
  private String description;

  @ManyToOne
  private UserAccountEntity userAccount;
}
