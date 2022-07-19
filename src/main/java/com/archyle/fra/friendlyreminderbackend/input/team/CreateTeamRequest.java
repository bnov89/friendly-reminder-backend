package com.archyle.fra.friendlyreminderbackend.input.team;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateTeamRequest {
    @NotNull
    private String name;
}
