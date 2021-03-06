package com.example.demo.controllers;


import static com.example.demo.security.SecurityConstants.HEADER_STRING;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Mock
  private UserRepository userRepo;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockHttpServletRequest request;

  private CreateUserRequest userRequest;

  private User user;

  @BeforeEach
  public void init() throws Exception {

    userRequest = new CreateUserRequest();
    userRequest.setUsername("TaoZhi");
    userRequest.setPassword("abcdefg");
    userRequest.setConfirmPassword("abcdefg");

    MvcResult entityResult = mockMvc.perform(
        MockMvcRequestBuilders.post("/api/user/create")
            .content(objectMapper.writeValueAsString(userRequest))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

    user = objectMapper.readValue(entityResult.getResponse().getContentAsString(), User.class);

    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post("/login")
            .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk()).andReturn();
    request.addParameter("Authorization", result.getResponse().getHeader("Authorization"));
  }

  @Test
  @SneakyThrows
  @DisplayName("Test method to test user fetching controllers using unique username and id")
  public void testGetUserWithUsernameAndId() {
    when(userRepo.findByUsername(Mockito.anyString())).thenReturn(user);
    when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{username}", "TaoZhi")
        .accept(MediaType.APPLICATION_JSON)
        .header(HEADER_STRING, request.getParameter(HEADER_STRING)))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{username}", "TaoZhi")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{username}", "TaoZhiTest")
        .accept(MediaType.APPLICATION_JSON)
        .header(HEADER_STRING, request.getParameter(HEADER_STRING)))
        .andExpect(status().isNotFound());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id/{id}", user.getId())
        .accept(MediaType.APPLICATION_JSON)
        .header(HEADER_STRING, request.getParameter(HEADER_STRING)))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id/{id}", user.getId())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/user/id/{id}", 100L)
        .accept(MediaType.APPLICATION_JSON)
        .header(HEADER_STRING, request.getParameter(HEADER_STRING)))
        .andExpect(status().isNotFound());

  }
}