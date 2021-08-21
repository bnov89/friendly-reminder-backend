package com.archyle.fra.friendlyreminderbackend.input;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class RegisterUserRequest {
    private String username;
    private String password;
}
