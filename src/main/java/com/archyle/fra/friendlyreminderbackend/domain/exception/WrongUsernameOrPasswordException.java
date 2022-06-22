package com.archyle.fra.friendlyreminderbackend.domain.exception;

public class WrongUsernameOrPasswordException extends RuntimeException {
  public WrongUsernameOrPasswordException(final String message) {
    super(message);
  }
}
