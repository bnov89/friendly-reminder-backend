package com.archyle.fra.friendlyreminderbackend.input.todo;

public class AccountNumberNotExistsException extends RuntimeException {
    public AccountNumberNotExistsException(String message) {
        super(message);
    }
}
