package com.example.bankapp.admin.service;

import com.example.bankapp.admin.web.dto.AdminUserRow;
import com.example.bankapp.auth.entity.User;
import com.example.bankapp.auth.repo.UserRepository;
import com.example.bankapp.customer.entity.CustomerProfile;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

  private final UserRepository userRepo;
  private final CustomerProfileRepository profileRepo;

  public AdminUserService(UserRepository userRepo, CustomerProfileRepository profileRepo) {
    this.userRepo = userRepo;
    this.profileRepo = profileRepo;
  }

  public List<AdminUserRow> getAllCustomersForDashboard() {

    List<User> users = userRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));

    Map<Long, CustomerProfile> profileByUserId = profileRepo.findAll().stream()
      .collect(Collectors.toMap(p -> p.getUser().getId(), Function.identity()));

    List<AdminUserRow> rows = new ArrayList<>();

    for (User u : users) {

      boolean isCustomer = u.getRoles().stream()
        .anyMatch(r -> "CUSTOMER".equalsIgnoreCase(r.getName()));

      if (!isCustomer) continue; // ✅ skip ADMIN

      AdminUserRow row = new AdminUserRow();
      row.setUserId(u.getId());
      row.setUsername(u.getUsername());
      row.setEnabled(u.isEnabled());
      row.setLocked(u.isLocked());
      row.setCreatedAt(u.getCreatedAt());

      String roles = u.getRoles().stream()
        .map(r -> r.getName())
        .sorted()
        .collect(Collectors.joining(", "));
      row.setRoles(roles);

      CustomerProfile p = profileByUserId.get(u.getId());
      if (p != null) {
        row.setCustomerId(p.getId());           // ✅ used in view accounts URL
        row.setFullName(p.getFullName());
        row.setPhone(p.getPhone());
        row.setKycStatus(p.getKycStatus().name());
      }

      rows.add(row);
    }

    return rows;
  }
}