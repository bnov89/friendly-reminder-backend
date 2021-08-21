package com.archyle.fra.friendlyreminderbackend.input;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
public class TestRestController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world";
    }

}
