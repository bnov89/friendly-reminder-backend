package com.archyle.fra.friendlyreminderbackend.input;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController()
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register")
    public Instant registerUser(@RequestBody RegisterUserRequest request) {
        return Instant.now();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return "User logged in " + request.toString();
    }

    @GetMapping
    public String getUser() {
        return "User returned";
    }

}
