package com.mediaSolutions.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;  


@RestController
public class AuthenticationController {

  @GetMapping("/check-auth")
  public ResponseEntity<String> checkAuthentication(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
      System.out.println("authentified");
      return ResponseEntity.ok("L'utilisateur est authentifié.");
    } else {
      System.out.println("unhauthentified");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("L'utilisateur n'est pas authentifié.");
    }
  }
}
