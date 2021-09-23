package com.archyle.fra.friendlyreminderbackend.input;

import org.springframework.stereotype.Service;

@Service
public class UserAccountNumberGeneratorImpl implements UserAccountNumberGenerator {
    @Override
    public String generate() {
        long leftLimit = 1000000L;
        long rightLimit = 9999999L;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return "FRA" + generatedLong;
    }
}
