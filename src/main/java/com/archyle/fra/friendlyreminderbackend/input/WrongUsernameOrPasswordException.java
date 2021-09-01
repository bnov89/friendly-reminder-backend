package com.archyle.fra.friendlyreminderbackend.input;

public class WrongUsernameOrPasswordException extends RuntimeException {
  public WrongUsernameOrPasswordException(final String message) {
    super(message);
  }
}
