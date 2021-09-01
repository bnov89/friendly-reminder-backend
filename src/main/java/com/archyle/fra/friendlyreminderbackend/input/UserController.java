package com.archyle.fra.friendlyreminderbackend.input;

import com.archyle.fra.friendlyreminderbackend.output.repository.UserAccountEntity;
import com.archyle.fra.friendlyreminderbackend.output.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserAccountRepository userAccountRepository;

  public UserController(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponse> registerUser(
      @RequestBody UserRegistrationRequest request) {
    userAccountRepository.save(
        UserAccountEntity.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    return userAccountRepository
        .findByUsernameAndPassword(request.getUsername(), request.getPassword())
        .map(
            userAccountEntity ->
                ResponseEntity.ok(LoginResponse.builder().accessToken(generateToken()).build()))
        .orElseThrow(() -> new WrongUsernameOrPasswordException("Wrong user name or password"));
  }

  private String generateToken() {
    return "SOME_TOKEN";
  }

  @GetMapping
  public String getUser() {
    return "User returned";
  }
}
