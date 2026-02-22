package com.example.bankapp.customer.web;

import com.example.bankapp.customer.service.RegistrationService;
import com.example.bankapp.customer.web.dto.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("form", new RegisterForm());
    return "auth/register";
  }

  @PostMapping("/register")
  public String registerSubmit(@Valid @ModelAttribute("form") RegisterForm form,
                               BindingResult br, Model model) {
    if (br.hasErrors()) return "auth/register";

    registrationService.register(form);
    model.addAttribute("message", "Registered successfully. Wait for admin approval to login.");
    return "auth/register-success";
  }
}
