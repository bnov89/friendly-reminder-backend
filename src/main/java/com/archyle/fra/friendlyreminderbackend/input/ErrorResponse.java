package com.archyle.fra.friendlyreminderbackend.input;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorResponse {
    String reason;
}
