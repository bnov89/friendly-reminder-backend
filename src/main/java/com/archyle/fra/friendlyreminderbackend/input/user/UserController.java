package com.archyle.fra.friendlyreminderbackend.input.user;

import com.archyle.fra.friendlyreminderbackend.domain.exception.UserNotFoundException;
import com.archyle.fra.friendlyreminderbackend.domain.exception.WrongUsernameOrPasswordException;
import com.archyle.fra.friendlyreminderbackend.input.*;
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

  private static final EnumSet<Products> ALL_AVAILABLE_PRODUCTS =
      EnumSet.of(Products.TODO, Products.MATCH_BET);


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
                                request.getUsername(), REGULAR_USER_AUTHORITIES, ALL_AVAILABLE_PRODUCTS))
                        .userAccountNumber(userAccountEntity.getUserAccountNumber())
                        .build()))
        .orElseThrow(() -> new WrongUsernameOrPasswordException("Wrong user name or password"));
  }

  @GetMapping("/{userAccountNumber}")
  public ResponseEntity<GetUserResponse> getUser(@PathVariable String userAccountNumber) {
    return userAccountRepository
        .findByUserAccountNumber(userAccountNumber)
        .map(
            uae ->
                ResponseEntity.ok(
                    GetUserResponse.builder()
                        .userAccountNumber(uae.getUserAccountNumber())
                        .username(uae.getUsername())
                        .build()))
        .orElseThrow(
            () ->
                new UserNotFoundException(
                    String.format(
                        "User not found for given account number: %s", userAccountNumber)));
  }
}
