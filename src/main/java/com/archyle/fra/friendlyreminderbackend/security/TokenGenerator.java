package com.archyle.fra.friendlyreminderbackend.security;

import java.util.EnumSet;

public interface TokenGenerator {

  String generate(Principal principal, EnumSet<Authorities> authorities, EnumSet<Products> products);
}
