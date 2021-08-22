package com.archyle.fra.friendlyreminderbackend.input;

import com.archyle.fra.friendlyreminderbackend.output.repository.UserAccountEntity;
import com.archyle.fra.friendlyreminderbackend.output.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {

  final UserAccountRepository userAccountRepository;

  public UserController(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  @PostMapping("/register")
  public ResponseEntity registerUser(@RequestBody UserRegistrationRequest request) {
    userAccountRepository.save(
        UserAccountEntity.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build());
    return ResponseEntity.ok().build();
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
