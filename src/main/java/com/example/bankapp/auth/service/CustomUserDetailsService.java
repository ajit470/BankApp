package com.example.bankapp.auth.service;

import com.example.bankapp.auth.repo.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepo;

  public CustomUserDetailsService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepo.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    var authorities = user.getRoles().stream()
      .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
      .collect(Collectors.toSet());

    return org.springframework.security.core.userdetails.User
      .withUsername(user.getUsername())
      .password(user.getPasswordHash())
      .authorities(authorities)
      .disabled(!user.isEnabled())
      .accountLocked(user.isLocked())
      .build();
  }
}