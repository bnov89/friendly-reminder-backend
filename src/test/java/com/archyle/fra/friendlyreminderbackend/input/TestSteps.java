package com.archyle.fra.friendlyreminderbackend.input;

import com.archyle.fra.friendlyreminderbackend.input.user.LoginRequest;
import com.archyle.fra.friendlyreminderbackend.input.user.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Service
public class TestSteps {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  public ResultActions createUser(UserRegistrationRequest request) throws Exception {

    return mockMvc.perform(
        post("/user/register")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

  public ResultActions login(LoginRequest request) throws Exception {
    return mockMvc.perform(
        post("/user/login")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

//  public ResultActions createTodoItem(CreateTodoItemRequest request, String accessToken)
//      throws Exception {
//    return mockMvc.perform(
//        post("/todo")
//            .header("Authorization", "Bearer " + accessToken)
//            .content(objectMapper.writeValueAsString(request))
//            .contentType(MediaType.APPLICATION_JSON));
//  }
}
