package com.archyle.fra.friendlyreminderbackend.input.todo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateTodoItemResponse {
  private Long id;
  private String description;
}

