package com.archyle.fra.friendlyreminderbackend.input.user;

import com.archyle.fra.friendlyreminderbackend.input.Authorities;
import com.archyle.fra.friendlyreminderbackend.input.Products;

import java.util.EnumSet;

public interface TokenGenerator {

  String generate(String username, EnumSet<Authorities> authorities, EnumSet<Products> products);
}
