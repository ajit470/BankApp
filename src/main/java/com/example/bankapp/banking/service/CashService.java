package com.example.bankapp.banking.service;

import com.example.bankapp.auth.repo.UserRepository;
import com.example.bankapp.banking.entity.*;
import com.example.bankapp.banking.repo.*;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import com.example.bankapp.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CashService {

  private final CustomerProfileRepository profileRepo;
  private final UserRepository userRepo;
  private final AccountRepository accountRepo;
  private final TransactionRepository txnRepo;
  private final LedgerEntryRepository ledgerRepo;

  public CashService(CustomerProfileRepository profileRepo,
                     UserRepository userRepo,
                     AccountRepository accountRepo,
                     TransactionRepository txnRepo,
                     LedgerEntryRepository ledgerRepo) {
    this.profileRepo = profileRepo;
    this.userRepo = userRepo;
    this.accountRepo = accountRepo;
    this.txnRepo = txnRepo;
    this.ledgerRepo = ledgerRepo;
  }

  @Transactional
  public String deposit(Long userId, Long accountId, BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException("Invalid amount");
    }

    var user = userRepo.findById(userId).orElseThrow(() -> new BusinessException("User not found"));
    var profile = profileRepo.findByUserId(userId).orElseThrow(() -> new BusinessException("Customer profile not found"));

    Account account = accountRepo.lockByIdAndCustomerId(accountId, profile.getId())
      .orElseThrow(() -> new BusinessException("Account not found / not yours"));

    if (account.getStatus() != AccountStatus.ACTIVE) {
      throw new BusinessException("Account is not ACTIVE");
    }

    Transaction txn = new Transaction();
    txn.setTxnType(TxnType.DEPOSIT);
    txn.setStatus(TxnStatus.SUCCESS);
    txn.setReference("DEP-" + System.currentTimeMillis());
    txn.setNarrative("Client deposit (simulated)");
    txn.setInitiatedBy(user);
    txnRepo.save(txn);

    account.setBalance(account.getBalance().add(amount));
    accountRepo.save(account);

    ledgerRepo.save(new LedgerEntry(txn, account, LedgerDirection.CREDIT, amount, account.getBalance()));
    return txn.getReference();
  }

  @Transactional
  public String withdraw(Long userId, Long accountId, BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessException("Invalid amount");
    }

    var user = userRepo.findById(userId).orElseThrow(() -> new BusinessException("User not found"));
    var profile = profileRepo.findByUserId(userId).orElseThrow(() -> new BusinessException("Customer profile not found"));

    Account account = accountRepo.lockByIdAndCustomerId(accountId, profile.getId())
      .orElseThrow(() -> new BusinessException("Account not found / not yours"));

    if (account.getStatus() != AccountStatus.ACTIVE) {
      throw new BusinessException("Account is not ACTIVE");
    }

    if (account.getBalance().compareTo(amount) < 0) {
      throw new BusinessException("Insufficient balance");
    }

    Transaction txn = new Transaction();
    txn.setTxnType(TxnType.WITHDRAWAL);
    txn.setStatus(TxnStatus.SUCCESS);
    txn.setReference("WDR-" + System.currentTimeMillis());
    txn.setNarrative("Client withdrawal (simulated)");
    txn.setInitiatedBy(user);
    txnRepo.save(txn);

    account.setBalance(account.getBalance().subtract(amount));
    accountRepo.save(account);

    ledgerRepo.save(new LedgerEntry(txn, account, LedgerDirection.DEBIT, amount, account.getBalance()));
    return txn.getReference();
  }
}
