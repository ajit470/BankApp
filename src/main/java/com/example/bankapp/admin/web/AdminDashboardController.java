package com.example.bankapp.admin.web;

import com.example.bankapp.admin.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

  private final AdminUserService adminUserService;

  public AdminDashboardController(AdminUserService adminUserService) {
    this.adminUserService = adminUserService;
  }

  @GetMapping("/admin/dashboard")
  public String dashboard(Model model) {
    model.addAttribute("users", adminUserService.getAllCustomersForDashboard());
    return "admin/dashboard";
  }
}