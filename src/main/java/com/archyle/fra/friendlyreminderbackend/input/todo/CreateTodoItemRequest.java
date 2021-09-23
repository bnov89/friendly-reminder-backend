package com.archyle.fra.friendlyreminderbackend.input.todo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTodoItemRequest {
    private String userNumber;
    private String description;
}
