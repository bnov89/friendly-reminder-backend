package com.archyle.fra.friendlyreminderbackend.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
  String username;
  String password;
}
