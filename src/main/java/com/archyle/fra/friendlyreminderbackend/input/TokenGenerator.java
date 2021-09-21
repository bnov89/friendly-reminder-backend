package com.archyle.fra.friendlyreminderbackend.input;

import java.util.EnumSet;

public interface TokenGenerator {

  String generate(String username, EnumSet<Authorities> authorities);
}
