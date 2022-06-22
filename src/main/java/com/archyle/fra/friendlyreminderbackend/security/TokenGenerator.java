package com.archyle.fra.friendlyreminderbackend.security;

import java.util.EnumSet;

public interface TokenGenerator {

  String generate(String username, EnumSet<Authorities> authorities, EnumSet<Products> products);
}
