package com.example.demo.controllers;

import static com.example.demo.security.SecurityConstants.HEADER_STRING;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CartControllerTest {

  @Mock private UserRepository userRepo;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private MockHttpServletRequest request;

  private User user;

  @BeforeEach
  public void init() throws Exception {

    CreateUserRequest userRequest = new CreateUserRequest();
    userRequest.setUsername("TaoZhi");
    userRequest.setPassword("abcdefg");
    userRequest.setConfirmPassword("abcdefg");

    MvcResult entityResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/user/create")
                    .content(objectMapper.writeValueAsString(userRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    user = objectMapper.readValue(entityResult.getResponse().getContentAsString(), User.class);

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/login")
                    .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isOk())
            .andReturn();
    request.addParameter("Authorization", result.getResponse().getHeader("Authorization"));
  }

  @Test
  @SneakyThrows
  @DisplayName("Test method to test addition add deletion of items in the cart")
  public void testCartTcontroller() {
    when(userRepo.findByUsername(Mockito.anyString())).thenReturn(user);

    ModifyCartRequest cartRequest =
        ModifyCartRequest.builder().itemId(1L).quantity(10).username("TaoZhi").build();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/cart/addToCart")
                .content(objectMapper.writeValueAsString(cartRequest))
                .header(HEADER_STRING, request.getParameter(HEADER_STRING))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/cart/addToCart")
                .content(objectMapper.writeValueAsString(cartRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());

    cartRequest.setUsername("TaoZhiTest");
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/cart/addToCart")
                .content(objectMapper.writeValueAsString(cartRequest))
                .header(HEADER_STRING, request.getParameter(HEADER_STRING))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    cartRequest.setUsername("TaoZhi");
    cartRequest.setQuantity(-10);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/cart/addToCart")
                .content(objectMapper.writeValueAsString(cartRequest))
                .header(HEADER_STRING, request.getParameter(HEADER_STRING))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    cartRequest.setQuantity(10);
    cartRequest.setItemId(100L);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/cart/addToCart")
                .content(objectMapper.writeValueAsString(cartRequest))
                .header(HEADER_STRING, request.getParameter(HEADER_STRING))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    cartRequest.setItemId(1L);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/cart/removeFromCart")
                .content(objectMapper.writeValueAsString(cartRequest))
                .header(HEADER_STRING, request.getParameter(HEADER_STRING))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
