package com.example.demo.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateLoginTest {

  @InjectMocks private UserController userController;

  @Mock private UserRepository userRepo;

  @Mock private CartRepository cartRepo;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private CreateUserRequest userRequest;

  @BeforeEach
  public void init() {
    userRequest = new CreateUserRequest();
    userRequest.setUsername("TaoZhi");
    userRequest.setPassword("abcdefg");
    userRequest.setConfirmPassword("abcdefg");
  }

  @Test
  @SneakyThrows
  @DisplayName("Test method to check the happy scenario of user creation with correct details")
  public void testCreateUserHappyScenario() {

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/user/create")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists())
        .andReturn();
  }

  @Test
  @SneakyThrows
  @DisplayName(
      "Test method to test check login of user witch correct(happy) and incorrect(unhappy) scenario")
  public void testCreateUserLoginScenario() {

    userRequest.setUsername("TaoZhi2");
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/user/create")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    userRequest.setPassword("123");
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/user/create")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    userRequest.setPassword("abcdefg");
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/login")
                .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk());

    userRequest.setUsername("TaoZhiTest");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/login")
                .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isUnauthorized());
  }
}
