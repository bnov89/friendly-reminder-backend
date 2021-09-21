package com.archyle.fra.friendlyreminderbackend.input;

import java.security.Key;

public class SigningKeyProviderImpl implements SigningKeyProvider {

  private final Key key;

  public SigningKeyProviderImpl(Key key) {
    this.key = key;
  }

  @Override
  public Key get() {
    return key;
  }
}
