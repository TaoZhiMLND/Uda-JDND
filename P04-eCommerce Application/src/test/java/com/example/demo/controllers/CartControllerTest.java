package com.example.demo.controllers;

import static com.example.demo.security.SecurityConstants.HEADER_STRING;
import static com.example.demo.security.SecurityConstants.TOKEN_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.LoginRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import lombok.SneakyThrows;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CartControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

/*  @Autowired
  private JacksonTester<LoginRequest> loginRequestjson;

  @Autowired
  private JacksonTester<CreateUserRequest> createUserRequestjson;*/


 /* @Autowired
  private MockHttpServletRequest request;*/

 /* private CreateUserRequest createUserRequest;*/

/*  private User user;*/


  @BeforeEach
  public void setup() throws Exception {

    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setUsername("TaoZhi");
    createUserRequest.setPassword("abcdefg");
    createUserRequest.setConfirmPassword("abcdefg");

    MvcResult entityResult = mvc.perform(
        MockMvcRequestBuilders.post("/api/user/create")
            .content(objectMapper.writeValueAsString(createUserRequest))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();

  /*  MvcResult entityResult=mockMvc.perform(
        MockMvcRequestBuilders.post("/api/user/create").content(objectMapper.writeValueAsString(userRequest))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();*/

    /*User user = objectMapper.readValue(entityResult.getResponse().getContentAsString(), User.class);
*/
    LoginRequest loginRequest = LoginRequest.builder().username("TaoZhi").password("abcdefg").build();
    MvcResult result = mvc
        .perform(MockMvcRequestBuilders.post("/login")
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk()).andReturn();

    //request.addParameter("Authorization", result.getResponse().getHeader("Authorization"));

/*    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post("/login").content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk()).andReturn();*/



    /*String token = mvc
        .perform(MockMvcRequestBuilders.post("/login").content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk()).andReturn().getResponse().getHeader(HEADER_STRING);*/

  /*  given(cartRepository.save(any())).willReturn(null);

    given(userRepository.findByUsername("andy")).willReturn(null);

    given(userRepository.findByUsername("TaoZhi")).willReturn(any());

    given(itemRepository.findById(1L)).willReturn(null);

    given(itemRepository.findById(2L)).willReturn(
        java.util.Optional.ofNullable(Item.builder().id(2L).name("HUAWEI").price(new BigDecimal
            (5000.0)).build()));*/
  }

  @Test
  @SneakyThrows
  public void addTocart_user_not_exists_should_rentrun_not_found() {
    /*ModifyCartRequest request = ModifyCartRequest.builder().itemId(1L).quantity(1)
        .username("andy").build();
    mvc.perform(
        post(new URI("/api/cart/addToCart")).header(HEADER_STRING, token)
            .content(modifyCartRequestjson.write(request).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());*/
  }

  @Test
  @SneakyThrows
  public void addTocart_user_exists_should_rentrun_ok() {
   /* ModifyCartRequest request = ModifyCartRequest.builder().itemId(1L).quantity(1)
        .username("TaoZhi").build();
    mvc.perform(
        post(new URI("/api/cart/addToCart")).header(HEADER_STRING, token)
            .content(modifyCartRequestjson.write(request).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());*/
  }

  @Test
  @SneakyThrows
  public void removeFromcart() {

   /* ModifyCartRequest request = ModifyCartRequest.builder().itemId(1L).quantity(1)
        .username("TaoZhi").build();
    mvc.perform(
        post(new URI("/api/cart/removeFromCart")).header(HEADER_STRING, token)
            .content(modifyCartRequestjson.write(request).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated());*/
  }

}