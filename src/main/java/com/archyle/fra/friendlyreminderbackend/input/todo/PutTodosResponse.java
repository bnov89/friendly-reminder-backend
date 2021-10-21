package com.archyle.fra.friendlyreminderbackend.input.todo;

import com.archyle.fra.friendlyreminderbackend.output.repository.TodoItemList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutTodosResponse {

    private TodoItemList todoItemList;
}
