package com.archyle.fra.friendlyreminderbackend.input;

import lombok.Data;

@Data
public class UserRegistrationRequest {
  private String username;
  private String password;
}
