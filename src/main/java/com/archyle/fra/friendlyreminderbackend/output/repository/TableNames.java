package com.archyle.fra.friendlyreminderbackend.output.repository;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class  TableNames {

  public static final String USER_TABLE_NAME = "users";

  public static final Set<String> TABLE_NAMES = Set.of(USER_TABLE_NAME);
}
