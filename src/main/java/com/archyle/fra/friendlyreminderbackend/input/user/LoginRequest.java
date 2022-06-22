package com.archyle.fra.friendlyreminderbackend.input.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
  private String username;
  private String password;
}
