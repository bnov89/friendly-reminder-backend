package com.archyle.fra.friendlyreminderbackend.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    initializers = AbstractIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
class UserControllerTest extends AbstractIntegrationTest {

  private static final String USERNAME = "Bart";
  private static final String PASSWORD = "passwd";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private TestSteps testSteps;
  @MockBean private TokenGenerator tokenGenerator;

  @Test
  void loginUser_whenUserNotExists_shouldReturn401() throws Exception {
    testSteps
        .login(createLoginRequest("notExistingUser", PASSWORD))
        .andExpect(status().isUnauthorized())
        .andExpect(content().string(containsString("\"reason\":\"Wrong user name or password\"")));
  }

  @Test
  void loginUser_whenUserExists_shouldLogin() throws Exception {
    Mockito.when(tokenGenerator.generate(eq(USERNAME), Mockito.any())).thenReturn("SOME_TOKEN");
    testSteps.createUser(new UserRegistrationRequest(USERNAME, PASSWORD));
    testSteps
        .login(createLoginRequest(USERNAME, PASSWORD))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("accessToken\":\"SOME_TOKEN")))
            .andExpect(content().string(containsString("userAccountNumber\":\"FRA")));
  }

  @Test
  void registerUser_shouldBeAvailableForAllUsersAndRegisterUser() throws Exception {
    testSteps
        .createUser(createUserRegistrationRequest(USERNAME, PASSWORD))
        .andExpect(status().isOk());
  }

  @Test
  void getUser_shouldBeForbiddenForNotLoggedInUsers() throws Exception {
    mockMvc.perform(get("/user")).andExpect(status().isForbidden());
  }

  @Test
  void registerUser_shouldBeAvailableForAllUsers() throws Exception {
    testSteps
        .createUser(createUserRegistrationRequest(USERNAME, PASSWORD))
        .andExpect(status().isOk());
  }

  private UserRegistrationRequest createUserRegistrationRequest(
      final String username, final String password) {
    return UserRegistrationRequest.builder().username(username).password(password).build();
  }

  private LoginRequest createLoginRequest(final String username, final String password) {
    return LoginRequest.builder().username(username).password(password).build();
  }
}
