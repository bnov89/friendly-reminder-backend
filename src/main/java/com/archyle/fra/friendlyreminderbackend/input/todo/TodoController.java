package com.archyle.fra.friendlyreminderbackend.input.todo;

import com.archyle.fra.friendlyreminderbackend.output.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

//  private final TodoRepository todoRepository;
  private final TodoListRepository todosRepository;
  private final UserAccountRepository userAccountRepository;

  @GetMapping("/user/{userAccountNumber}")
  public ResponseEntity<TodoListResponse> getTodos(@PathVariable String userAccountNumber) {
    TodoListResponse todoListResponse =
        todosRepository
            .findTodoListEntityByUserAccountUserAccountNumber(userAccountNumber)
            .map(TodoListEntity::getTodoItemList)
            .map(todoItemList -> TodoListResponse.builder().todoItemList(todoItemList).build())
            .orElse(TodoListResponse.builder().todoItemList(new TodoItemList()).build());
    return ResponseEntity.ok(todoListResponse);
  }

  @PutMapping("/user/{userAccountNumber}")
  public ResponseEntity<PutTodosResponse> putTodos(
      @RequestBody PutTodosRequest request, @PathVariable String userAccountNumber) {
    TodoListEntity savedEntity =
        todosRepository
            .findTodoListEntityByUserAccountUserAccountNumber(userAccountNumber)
            .map(
                todoListEntity -> {
                  todoListEntity.setTodoItemList(request.getTodoItemList());
                  return todoListEntity;
                })
            .map(todosRepository::save)
            .orElseGet(() -> saveNewTodoLIst(userAccountNumber, request.getTodoItemList()));
    return ResponseEntity.ok(
        PutTodosResponse.builder().todoItemList(savedEntity.getTodoItemList()).build());
  }

  private TodoListEntity saveNewTodoLIst(String userAccountNumber, TodoItemList todoItemList) {
    Optional<UserAccountEntity> userAccount =
        userAccountRepository.findByUserAccountNumber(userAccountNumber);
    return todosRepository.save(
        TodoListEntity.builder().userAccount(userAccount.get()).todoItemList(todoItemList).build());
  }
}
