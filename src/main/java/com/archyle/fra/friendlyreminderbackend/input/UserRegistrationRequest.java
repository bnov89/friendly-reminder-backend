package com.archyle.fra.friendlyreminderbackend.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationRequest {
  private String username;
  private String password;
}
