package com.archyle.fra.friendlyreminderbackend.output.repository;

import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<TodoItemEntity, Long> {
}
