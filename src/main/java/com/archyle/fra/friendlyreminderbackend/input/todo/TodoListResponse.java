package com.archyle.fra.friendlyreminderbackend.input.todo;

import com.archyle.fra.friendlyreminderbackend.output.repository.TodoItemList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListResponse {
    private TodoItemList todoItemList;
}
