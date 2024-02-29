package com.mediaSolutions.gateway.configuration;

import java.net.URI;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
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

/*   @Autowired
  private DataSource dataSource; */

  /**
   * Some javadoc :
   * 
   * This method represents the configuration of authentification fron a database.
   */
  /*@Autowired
  public void configAuthentification(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
        .dataSource(dataSource)
        .usersByUsernameQuery("SELECT username, password, 'true' FROM user WHERE username=?")
        .authoritiesByUsernameQuery("SELECT username, 'true' FROM user WHERE username=?");
  }*/

  @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user")).build();

        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public ServerLogoutSuccessHandler logoutSuccessHandler() {
        RedirectServerLogoutSuccessHandler handler = new RedirectServerLogoutSuccessHandler();
        handler.setLogoutSuccessUrl(URI.create("/patient/list"));
        return handler;
    }

  /**
   * Some javadoc :
   * 
   * This method represents the configuration of each authorized or not request on
   * the app.
   * Its detemine how and which file are authorized. The login page, the success
   * defauflt URL or many
   * other URL are dertimined here.
   * 
   * @param http represent the basic configuration.
   * 
   * @return the filter chain.
   */
  /* @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
    http.authorizeExchange(auth -> {
      auth.pathMatchers("/css/**", "/js/**").permitAll();
      auth.anyExchange().authenticated();
    })
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutSuccessHandler())
            .permitAll())

        .formLogin(form -> form
            .defaultSuccessUrl("http://localhost:9000/patient/list", true)
            .permitAll());

    return http.build();
  } */

  @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange(exchanges -> {
                    exchanges.pathMatchers("http://localhost:9000/**").authenticated();// TODO CHECKER URL
                    exchanges.anyExchange().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(logout -> {
                    logout.logoutSuccessHandler(logoutSuccessHandler());
                }).csrf(ServerHttpSecurity.CsrfSpec::disable).build(); //TODO vérifier
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