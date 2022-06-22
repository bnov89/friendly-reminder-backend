package com.archyle.fra.friendlyreminderbackend.input.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private String username;
    private String userAccountNumber;
}
