package com.archyle.fra.friendlyreminderbackend.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Principal {
    private final String username;
    private final String userAccountNumber;
}
