package com.archyle.fra.friendlyreminderbackend.input;

import com.archyle.fra.friendlyreminderbackend.output.repository.UserAccountEntity;
import com.archyle.fra.friendlyreminderbackend.output.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private static final EnumSet<Authorities> REGULAR_USER_AUTHORITIES =
      EnumSet.of(Authorities.REGULAR_USER);
  private final UserAccountRepository userAccountRepository;
  private final TokenGenerator tokenGenerator;
  private final UserAccountNumberGenerator userAccountNumberGenerator;

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponse> registerUser(
      @RequestBody UserRegistrationRequest request) {
    userAccountRepository.save(
        UserAccountEntity.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .userAccountNumber(userAccountNumberGenerator.generate())
            .build());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    return userAccountRepository
        .findByUsernameAndPassword(request.getUsername(), request.getPassword())
        .map(
            userAccountEntity ->
                ResponseEntity.ok(
                    LoginResponse.builder()
                        .accessToken(
                            tokenGenerator.generate(
                                request.getUsername(), REGULAR_USER_AUTHORITIES))
                        .userAccountNumber(userAccountEntity.getUserAccountNumber())
                        .build()))
        .orElseThrow(() -> new WrongUsernameOrPasswordException("Wrong user name or password"));
  }

  @GetMapping
  public String getUser() {
    return "User returned";
  }
}
