package com.example.bankapp.banking.web;

import com.example.bankapp.auth.repo.UserRepository;
import com.example.bankapp.banking.repo.AccountRepository;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientDashboardController {

  private final UserRepository userRepo;
  private final CustomerProfileRepository profileRepo;
  private final AccountRepository accountRepo;

  public ClientDashboardController(UserRepository userRepo,
                                   CustomerProfileRepository profileRepo,
                                   AccountRepository accountRepo) {
    this.userRepo = userRepo;
    this.profileRepo = profileRepo;
    this.accountRepo = accountRepo;
  }

  @GetMapping("/client/dashboard")
  public String dashboard(Authentication auth, Model model) {
    var user = userRepo.findByUsername(auth.getName()).orElseThrow();
    var profile = profileRepo.findByUserId(user.getId()).orElseThrow();

    model.addAttribute("profile", profile);
    model.addAttribute("accounts", accountRepo.findByCustomer(profile));
    return "client/dashboard";
  }
}