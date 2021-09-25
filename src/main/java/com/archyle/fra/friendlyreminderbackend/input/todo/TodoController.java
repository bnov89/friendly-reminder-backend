package com.archyle.fra.friendlyreminderbackend.input.todo;

import com.archyle.fra.friendlyreminderbackend.output.repository.TodoItemEntity;
import com.archyle.fra.friendlyreminderbackend.output.repository.TodoRepository;
import com.archyle.fra.friendlyreminderbackend.output.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

  private final TodoRepository todoRepository;
  private final UserAccountRepository userAccountRepository;

  @GetMapping("/user/{userAccountNumber}")
  public ResponseEntity<TodoListResponse> getTodos(@PathVariable String userAccountNumber) {
    List<TodoItemEntity> queryResult =
        todoRepository.findTodoItemEntitiesByUserAccountNumber(userAccountNumber);
    return ResponseEntity.ok(TodoListResponse.builder().todoItemList(queryResult).build());
  }

  @PostMapping
  public ResponseEntity<CreateTodoItemResponse> createTodoItem(
      @RequestBody CreateTodoItemRequest request) {
    TodoItemEntity todoItemEntity =
        userAccountRepository
            .findByUserAccountNumber(request.getUserNumber())
            .map(
                uae ->
                    todoRepository.save(
                        TodoItemEntity.builder()
                            .description(request.getDescription())
                            .userAccount(uae)
                            .build()))
            .orElseThrow(
                () ->
                    new AccountNumberNotExistsException(
                        String.format(
                            "User account with given number %s doesn't exist!",
                            request.getUserNumber())));

    return ResponseEntity.ok()
        .body(
            CreateTodoItemResponse.builder()
                .id(todoItemEntity.getId())
                .description(todoItemEntity.getDescription())
                .build());
  }
}