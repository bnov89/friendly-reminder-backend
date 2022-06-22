package com.archyle.fra.friendlyreminderbackend.input.todo;

import com.archyle.fra.friendlyreminderbackend.input.*;
import com.archyle.fra.friendlyreminderbackend.input.user.LoginRequest;
import com.archyle.fra.friendlyreminderbackend.input.user.LoginResponse;
import com.archyle.fra.friendlyreminderbackend.input.user.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    initializers = AbstractIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Testcontainers
class TodoControllerTest extends AbstractIntegrationTest {
  private static final String USERNAME = "Bart";
  private static final String PASSWORD = "passwd";
  @Autowired private MockMvc mockMvc;
  @Autowired private TestSteps testSteps;
  @Autowired private ObjectMapper objectMapper;

  @Test
  @Disabled
  void shouldCreateTodoItem() throws Exception {
    testSteps.createUser(
        UserRegistrationRequest.builder().username(USERNAME).password(PASSWORD).build());
    MvcResult mvcResult = testSteps.login(createLoginRequest(USERNAME, PASSWORD)).andReturn();
    LoginResponse loginResponse =
        objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LoginResponse.class);
    ResultActions result =
        testSteps.createTodoItem(
            CreateTodoItemRequest.builder()
                .userNumber(loginResponse.getUserAccountNumber())
                .description("First thing needs to be done")
                .build(), loginResponse.getAccessToken());
    CreateTodoItemResponse createTodoItemResponse =
        objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(), CreateTodoItemResponse.class);
    Assertions.assertThat(createTodoItemResponse.getDescription())
        .isEqualTo("First thing needs to be done");
    Assertions.assertThat(createTodoItemResponse.getId()).isNotNull();
  }

  private LoginRequest createLoginRequest(final String username, final String password) {
    return LoginRequest.builder().username(username).password(password).build();
  }
}
