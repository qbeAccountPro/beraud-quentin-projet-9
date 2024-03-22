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
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

  /**
   * Some Javadoc :
   * 
   * Bean method to create a test user with username "user" and password "user".
   *
   * @return a MapReactiveUserDetailsService containing the test user.
   */
  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user")).build();
    return new MapReactiveUserDetailsService(user);
  }

  /**
   * Some Javadoc :
   * 
   * Configure the logout success handler to redirect to the login
   * page after logout.
   *
   * @return a RedirectServerLogoutSuccessHandler for handling logout success.
   */
  @Bean
  public ServerLogoutSuccessHandler logoutSuccessHandler() {
    RedirectServerLogoutSuccessHandler handler = new RedirectServerLogoutSuccessHandler();
    handler.setLogoutSuccessUrl(URI.create("/login"));
    return handler;
  }

  /**
   * Some Javadoc :
   * 
   * This method configures the authentication success handler to redirect to the
   * patient list page after login.
   *
   * @return a RedirectServerAuthenticationSuccessHandler for handling
   *         authentication success.
   */
  @Bean
  public ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
    RedirectServerAuthenticationSuccessHandler handler = new RedirectServerAuthenticationSuccessHandler();
    handler.setLocation(URI.create("/patient/list"));
    return handler;
  }

  /**
   * Some Javadoc :
   * 
   * This method configures the security filter chain for the application.
   *
   * @param http the ServerHttpSecurity object to configure security settings.
   * @return a SecurityWebFilterChain object representing the configured security
   *         filter chain.
   * @throws Exception if an error occurs during configuration.
   */
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
          logout.logoutUrl("/app-logout");
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