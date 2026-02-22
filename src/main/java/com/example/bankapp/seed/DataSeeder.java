package com.example.bankapp.seed;

import com.example.bankapp.auth.entity.Role;
import com.example.bankapp.auth.entity.User;
import com.example.bankapp.auth.repo.RoleRepository;
import com.example.bankapp.auth.repo.UserRepository;
import com.example.bankapp.banking.entity.AccountType;
import com.example.bankapp.banking.repo.AccountTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

  private final RoleRepository roleRepo;
  private final UserRepository userRepo;
  private final AccountTypeRepository accountTypeRepo;
  private final PasswordEncoder encoder;

  public DataSeeder(RoleRepository roleRepo, UserRepository userRepo,
                    AccountTypeRepository accountTypeRepo, PasswordEncoder encoder) {
    this.roleRepo = roleRepo;
    this.userRepo = userRepo;
    this.accountTypeRepo = accountTypeRepo;
    this.encoder = encoder;
  }

  @Override
  public void run(String... args) {
    createRoleIfMissing("ADMIN");
    createRoleIfMissing("CUSTOMER");

    createAccountTypeIfMissing("SAVINGS", "Savings Account");
    createAccountTypeIfMissing("CURRENT", "Current Account");
    createAccountTypeIfMissing("FD", "Fixed Deposit");

    createAdminIfMissing();
  }

  private void createRoleIfMissing(String name) {
    roleRepo.findByName(name).orElseGet(() -> {
      Role r = new Role();
      r.setName(name);
      return roleRepo.save(r);
    });
  }

  private void createAccountTypeIfMissing(String code, String displayName) {
    accountTypeRepo.findByCode(code).orElseGet(() -> {
      AccountType t = new AccountType();
      t.setCode(code);
      t.setDisplayName(displayName);
      return accountTypeRepo.save(t);
    });
  }

  private void createAdminIfMissing() {
    String adminEmail = "admin@bank.com";
    if (userRepo.existsByUsername(adminEmail)) return;

    Role adminRole = roleRepo.findByName("ADMIN").orElseThrow();

    User admin = new User();
    admin.setUsername(adminEmail);
    admin.setPasswordHash(encoder.encode("admin123"));
    admin.setEnabled(true);
    admin.getRoles().add(adminRole);

    userRepo.save(admin);
  }
}
