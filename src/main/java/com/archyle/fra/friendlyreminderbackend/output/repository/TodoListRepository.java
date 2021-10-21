package com.archyle.fra.friendlyreminderbackend.output.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TodoListRepository extends CrudRepository<TodoListEntity, Long> {
  Optional<TodoListEntity> findTodoListEntityByUserAccountUserAccountNumber(
      String userAccountNumber);
}
