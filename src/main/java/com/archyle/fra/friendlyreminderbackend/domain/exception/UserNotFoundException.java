package com.archyle.fra.friendlyreminderbackend.domain.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
