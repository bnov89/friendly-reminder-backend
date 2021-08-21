package com.archyle.fra.friendlyreminderbackend.input;

import lombok.Value;

@Value
public class LoginRequest {
    String username;
    String password;
}
