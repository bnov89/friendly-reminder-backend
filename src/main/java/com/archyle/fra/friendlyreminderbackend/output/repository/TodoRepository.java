package com.archyle.fra.friendlyreminderbackend.output.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<TodoItemEntity, Long> {
    List<TodoItemEntity> findTodoItemEntitiesByUserAccountNumber(String userAccountNumber);
}
