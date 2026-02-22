package com.example.bankapp.banking.service;

import com.example.bankapp.banking.entity.Account;
import com.example.bankapp.banking.entity.AccountStatus;
import com.example.bankapp.banking.repo.AccountRepository;
import com.example.bankapp.banking.repo.AccountTypeRepository;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import com.example.bankapp.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
public class AccountOpeningService {

  private final CustomerProfileRepository profileRepo;
  private final AccountRepository accountRepo;
  private final AccountTypeRepository typeRepo;

  public AccountOpeningService(CustomerProfileRepository profileRepo,
                               AccountRepository accountRepo,
                               AccountTypeRepository typeRepo) {
    this.profileRepo = profileRepo;
    this.accountRepo = accountRepo;
    this.typeRepo = typeRepo;
  }

  @Transactional
  public void openSavingsAccount(Long customerId) {
    var customer = profileRepo.findById(customerId)
      .orElseThrow(() -> new BusinessException("Customer not found"));

    var savings = typeRepo.findByCode("SAVINGS")
      .orElseThrow(() -> new BusinessException("SAVINGS type missing"));

    Account acc = new Account();
    acc.setCustomer(customer);
    acc.setAccountType(savings);
    acc.setAccountNo(generateAccountNo());
    acc.setStatus(AccountStatus.ACTIVE);

    accountRepo.save(acc);
  }

  private String generateAccountNo() {
    SecureRandom r = new SecureRandom();
    long n = 1000000000L + Math.abs(r.nextLong() % 9000000000L);
    return "21" + n;
  }
}