package com.example.bankapp.admin.web;

import com.example.bankapp.admin.service.AdminKycService;
import com.example.bankapp.customer.entity.KycStatus;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminKycController {

  private final CustomerProfileRepository profileRepo;
  private final AdminKycService adminKycService;

  public AdminKycController(CustomerProfileRepository profileRepo, AdminKycService adminKycService) {
    this.profileRepo = profileRepo;
    this.adminKycService = adminKycService;
  }

  @GetMapping("/admin/kyc/pending")
  public String pending(Model model) {
    model.addAttribute("customers", profileRepo.findByKycStatus(KycStatus.PENDING));
    return "admin/kyc-pending";
  }

  @PostMapping("/admin/kyc/{id}/approve")
  public String approve(@PathVariable Long id) {
    adminKycService.approve(id);
    return "redirect:/admin/kyc/pending";
  }

  @PostMapping("/admin/kyc/{id}/reject")
  public String reject(@PathVariable Long id) {
    adminKycService.reject(id);
    return "redirect:/admin/kyc/pending";
  }
}