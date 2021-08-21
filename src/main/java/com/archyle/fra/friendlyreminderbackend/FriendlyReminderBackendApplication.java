package com.archyle.fra.friendlyreminderbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FriendlyReminderBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendlyReminderBackendApplication.class, args);
    }

}
