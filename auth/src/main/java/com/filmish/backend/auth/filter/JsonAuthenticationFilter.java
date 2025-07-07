package com.filmish.backend.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmish.backend.auth.filter.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
      new AntPathRequestMatcher("/login", "POST");
  private final ObjectMapper objectMapper = new ObjectMapper();

  public JsonAuthenticationFilter() {
    super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequest loginRequest =
          objectMapper.readValue(request.getInputStream(), LoginRequest.class);

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          loginRequest.username(), loginRequest.password(), Collections.emptyList());

      this.setDetails(request, authToken);

      return this.getAuthenticationManager().authenticate(authToken);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected void setDetails(
      HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }
}
