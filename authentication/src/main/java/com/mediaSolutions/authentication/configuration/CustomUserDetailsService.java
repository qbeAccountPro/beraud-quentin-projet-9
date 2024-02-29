package com.mediaSolutions.authentication.configuration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mediaSolutions.authentication.repository.UserRepository;

/**
 * Some javadoc :
 * 
 * This class represent the User Details Service.
 * 
 * It configure the information to allow the establishment of persistent token
 * for the connection.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    com.mediaSolutions.authentication.model.User user = userRepository.findByMail(email);

    if (user == null) {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }
    return User.withUsername(user.getMail())
        .password(user.getPassword())
        .roles("USER")
        .build();
  }
}