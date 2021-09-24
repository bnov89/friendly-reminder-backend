package com.archyle.fra.friendlyreminderbackend.input;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
