package com.example.bankapp.admin.service;

import com.example.bankapp.auth.repo.UserRepository;
import com.example.bankapp.banking.service.AccountOpeningService;
import com.example.bankapp.customer.entity.KycStatus;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import com.example.bankapp.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminKycService {

  private final CustomerProfileRepository profileRepo;
  private final UserRepository userRepo;
  private final AccountOpeningService accountOpeningService;

  public AdminKycService(CustomerProfileRepository profileRepo,
                         UserRepository userRepo,
                         AccountOpeningService accountOpeningService) {
    this.profileRepo = profileRepo;
    this.userRepo = userRepo;
    this.accountOpeningService = accountOpeningService;
  }

  @Transactional
  public void approve(Long customerId) {
    var profile = profileRepo.findById(customerId)
      .orElseThrow(() -> new BusinessException("Customer not found"));

    profile.setKycStatus(KycStatus.VERIFIED);
    profileRepo.save(profile);

    var user = profile.getUser();
    user.setEnabled(true);
    userRepo.save(user);

    accountOpeningService.openSavingsAccount(profile.getId());
  }

  @Transactional
  public void reject(Long customerId) {
    var profile = profileRepo.findById(customerId)
      .orElseThrow(() -> new BusinessException("Customer not found"));
    profile.setKycStatus(KycStatus.REJECTED);
    profileRepo.save(profile);
  }
}