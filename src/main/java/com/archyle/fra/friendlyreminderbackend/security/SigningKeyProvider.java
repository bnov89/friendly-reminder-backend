package com.archyle.fra.friendlyreminderbackend.security;

import java.security.Key;

public interface SigningKeyProvider {
    Key get();
}
