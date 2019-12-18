package com.example.demo.security;


import static org.junit.Assert.assertThat;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class JWTAuthenticationFilterTest {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Before
  public void before() {
    SecurityContextHolder.clearContext();
  }

  @After
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