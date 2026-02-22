package com.example.bankapp.admin.web;

import com.example.bankapp.banking.repo.AccountRepository;
import com.example.bankapp.banking.repo.LedgerEntryRepository;
import com.example.bankapp.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminAccountTransactionsController {

  private final AccountRepository accountRepo;
  private final LedgerEntryRepository ledgerRepo;

  public AdminAccountTransactionsController(AccountRepository accountRepo,
                                            LedgerEntryRepository ledgerRepo) {
    this.accountRepo = accountRepo;
    this.ledgerRepo = ledgerRepo;
  }

  @GetMapping("/admin/accounts/{accountId}/transactions")
  public String accountTransactions(@PathVariable Long accountId, Model model) {

    var account = accountRepo.findById(accountId)
      .orElseThrow(() -> new BusinessException("Account not found"));

    model.addAttribute("account", account);
    model.addAttribute("entries", ledgerRepo.findTop50ByAccountIdOrderByCreatedAtDesc(accountId));
    return "admin/account-transactions";
  }
}
