package com.example.bankapp.auth.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  @GetMapping("/login")
  public String login() {
    return "auth/login";
  }

  @GetMapping("/post-login")
  public String postLogin(Authentication auth) {
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    if (isAdmin) return "redirect:/admin/dashboard";
    return "redirect:/client/dashboard";
  }
}
