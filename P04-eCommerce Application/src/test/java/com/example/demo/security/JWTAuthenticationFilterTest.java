package com.example.demo.security;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class JWTAuthenticationFilterTest {

  @BeforeEach
  public void before() {
    SecurityContextHolder.clearContext();
  }

  @AfterEach
  public void after() {
    SecurityContextHolder.clearContext();
  }

  @Test
  @SneakyThrows
  public void doFilterInternal_shouldPopulateSecurityContext_whenTokenIsValid() {
    Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
  }
}
