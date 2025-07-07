package com.filmish.backend.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmish.backend.auth.filter.JsonAuthenticationFilter;
import com.filmish.backend.auth.support.error.AuthErrorType;
import com.filmish.backend.auth.support.response.AuthApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, JsonAuthenticationFilter jsonAuthenticationFilter) throws Exception {
    http.httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .securityContext(securityContext ->
            securityContext.securityContextRepository(securityContextRepository()))
        .authorizeHttpRequests(auth -> auth.requestMatchers("/login")
            .permitAll()
            .requestMatchers("/signup")
            .permitAll()
            .anyRequest()
            .authenticated())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .logoutSuccessHandler((request, response, authentication) -> {
              response.setStatus(HttpServletResponse.SC_OK);
              response.setContentType(MediaType.APPLICATION_JSON_VALUE);
              response.setCharacterEncoding(StandardCharsets.UTF_8.name());
              response
                  .getWriter()
                  .write(objectMapper.writeValueAsString(AuthApiResponse.success()));
            }))
        .addFilterBefore(jsonAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public JsonAuthenticationFilter jsonAuthenticationFilter(
      AuthenticationManager authenticationManager,
      SecurityContextRepository securityContextRepository) {
    JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
    filter.setAuthenticationSuccessHandler(successHandler());
    filter.setAuthenticationFailureHandler(failureHandler());
    filter.setAuthenticationManager(authenticationManager);
    filter.setFilterProcessesUrl("/login"); // 핵심: 처리 URL 지정
    filter.setSecurityContextRepository(securityContextRepository);
    return filter;
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return (request, response, authentication) -> {
      SecurityContextHolder.getContext().setAuthentication(authentication);
      HttpSession session = request.getSession(true);
      session.setAttribute(
          HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
          SecurityContextHolder.getContext());

      response.setStatus(HttpServletResponse.SC_OK);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.getWriter().write(objectMapper.writeValueAsString(AuthApiResponse.success()));
    };
  }

  @Bean
  public AuthenticationFailureHandler failureHandler() {
    return (request, response, exception) -> {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response
          .getWriter()
          .write(objectMapper.writeValueAsString(
              AuthApiResponse.error(AuthErrorType.AUTHENTICATION_FAILED)));
    };
  }
}
