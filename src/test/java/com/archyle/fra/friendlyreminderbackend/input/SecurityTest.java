package com.archyle.fra.friendlyreminderbackend.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerUser_shouldBeAvailableForAllUsers() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("bart");
        request.setPassword("passwd");
        mockMvc.perform(post("/user/register")
                        .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUser_shouldBeAvailableForAllUsers() throws Exception {
        LoginRequest request = new LoginRequest("bart", "passwd");
        mockMvc.perform(post("/user/login")
                        .content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser_shouldBeForbiddenForNotLoggedInUsers() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isForbidden());
    }


}