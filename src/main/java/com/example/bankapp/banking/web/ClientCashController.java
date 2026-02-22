package com.example.bankapp.banking.web;

import com.example.bankapp.auth.repo.UserRepository;
import com.example.bankapp.banking.repo.AccountRepository;
import com.example.bankapp.banking.service.CashService;
import com.example.bankapp.banking.web.dto.MoneyForm;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientCashController {

  private final UserRepository userRepo;
  private final CustomerProfileRepository profileRepo;
  private final AccountRepository accountRepo;
  private final CashService cashService;

  public ClientCashController(UserRepository userRepo,
                              CustomerProfileRepository profileRepo,
                              AccountRepository accountRepo,
                              CashService cashService) {
    this.userRepo = userRepo;
    this.profileRepo = profileRepo;
    this.accountRepo = accountRepo;
    this.cashService = cashService;
  }

  @GetMapping("/client/deposit")
  public String depositForm(Authentication auth, Model model) {
    var user = userRepo.findByUsername(auth.getName()).orElseThrow();
    var profile = profileRepo.findByUserId(user.getId()).orElseThrow();

    model.addAttribute("accounts", accountRepo.findByCustomer(profile));
    model.addAttribute("form", new MoneyForm());
    return "client/deposit";
  }

  @PostMapping("/client/deposit")
  public String doDeposit(Authentication auth,
                          @Valid @ModelAttribute("form") MoneyForm form,
                          BindingResult br,
                          RedirectAttributes ra,
                          Model model) {

    var user = userRepo.findByUsername(auth.getName()).orElseThrow();
    var profile = profileRepo.findByUserId(user.getId()).orElseThrow();

    if (br.hasErrors()) {
      model.addAttribute("accounts", accountRepo.findByCustomer(profile));
      return "client/deposit";
    }

    String ref = cashService.deposit(user.getId(), form.getAccountId(), form.getAmount());
    ra.addFlashAttribute("success", "Deposit successful. Ref: " + ref);
    return "redirect:/client/dashboard";
  }

  @GetMapping("/client/withdraw")
  public String withdrawForm(Authentication auth, Model model) {
    var user = userRepo.findByUsername(auth.getName()).orElseThrow();
    var profile = profileRepo.findByUserId(user.getId()).orElseThrow();

    model.addAttribute("accounts", accountRepo.findByCustomer(profile));
    model.addAttribute("form", new MoneyForm());
    return "client/withdraw";
  }

  @PostMapping("/client/withdraw")
  public String doWithdraw(Authentication auth,
                           @Valid @ModelAttribute("form") MoneyForm form,
                           BindingResult br,
                           RedirectAttributes ra,
                           Model model) {

    var user = userRepo.findByUsername(auth.getName()).orElseThrow();
    var profile = profileRepo.findByUserId(user.getId()).orElseThrow();

    if (br.hasErrors()) {
      model.addAttribute("accounts", accountRepo.findByCustomer(profile));
      return "client/withdraw";
    }

    String ref = cashService.withdraw(user.getId(), form.getAccountId(), form.getAmount());
    ra.addFlashAttribute("success", "Withdrawal successful. Ref: " + ref);
    return "redirect:/client/dashboard";
  }
}