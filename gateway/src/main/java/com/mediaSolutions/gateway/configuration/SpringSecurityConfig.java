package com.mediaSolutions.gateway.configuration;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

/**
 * Some javadoc :
 * 
 * This class represents the configuration of Spring Security.
 */ 
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

  /**
   * Some javadoc :
   * 
   * This method is used to create a test user.
   */
  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user")).build();
    return new MapReactiveUserDetailsService(user);
  }

  @Bean
  public ServerLogoutSuccessHandler logoutSuccessHandler() {
    RedirectServerLogoutSuccessHandler handler = new RedirectServerLogoutSuccessHandler();
    handler.setLogoutSuccessUrl(URI.create("/login"));
    return handler;
  }

  @Bean
  public ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
    RedirectServerAuthenticationSuccessHandler handler = new RedirectServerAuthenticationSuccessHandler();
    handler.setLocation(URI.create("/patient/list"));
    return handler;
  }

  @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
    return http
        .authorizeExchange(exchanges -> {
          exchanges.pathMatchers("http://localhost:9000/**").authenticated();
          exchanges.anyExchange().authenticated();
        })
        .httpBasic(Customizer.withDefaults())
        .formLogin(form -> {
          form.authenticationSuccessHandler(authenticationSuccessHandler());
        })
        .logout(logout -> {
          logout.logoutSuccessHandler(logoutSuccessHandler());
        }).csrf(ServerHttpSecurity.CsrfSpec::disable).build();
  }

  /**
   * Some javadoc :
   * 
   * Service interface for encoding passwords. The preferred implementation is
   * BCryptPasswordEncoder
   * 
   * @return the encoding passwords.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}