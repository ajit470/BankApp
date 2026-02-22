package com.example.bankapp.admin.web;

import com.example.bankapp.banking.repo.AccountRepository;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import com.example.bankapp.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminCustomerAccountsController {

  private final CustomerProfileRepository profileRepo;
  private final AccountRepository accountRepo;

  public AdminCustomerAccountsController(CustomerProfileRepository profileRepo,
                                         AccountRepository accountRepo) {
    this.profileRepo = profileRepo;
    this.accountRepo = accountRepo;
  }

  @GetMapping("/admin/customers/{customerId}/accounts")
  public String customerAccounts(@PathVariable Long customerId, Model model) {

    var customer = profileRepo.findById(customerId)
      .orElseThrow(() -> new BusinessException("Customer not found"));

    model.addAttribute("customer", customer);
    model.addAttribute("accounts", accountRepo.findByCustomerId(customerId));
    return "admin/customer-accounts";
  }
}
