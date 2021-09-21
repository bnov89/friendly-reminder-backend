package com.archyle.fra.friendlyreminderbackend.input;

import java.security.Key;

public interface SigningKeyProvider {
    Key get();
}
