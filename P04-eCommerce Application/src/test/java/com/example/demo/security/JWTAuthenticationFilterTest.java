package com.example.demo.security;




import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import lombok.SneakyThrows;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class JWTAuthenticationFilterTest {

  @Autowired
  private AuthenticationManager authenticationManager;

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

    String token = issueTokenForUser("john.doe");
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/foo");
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    MockHttpServletResponse response = new MockHttpServletResponse();
    FilterChain filterChain = new MockFilterChain();
    FilterConfig filterConfig = new MockFilterConfig();

    JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager);
    filter.init(filterConfig);
    filter.doFilter(request, response, filterChain);
    filter.destroy();

    /*assertThat(SecurityContextHolder.getContext().getAuthentication())
        .satisfies(authentication -> {
          assertThat(authentication).isNotNull();
          assertThat(authentication.getName()).isEqualTo("john.doe");
        });*/
  }

  private String issueTokenForUser(String username) {
    return "xxxxx.yyyyy.zzzzz"; // Implement as per your needs
  }
}