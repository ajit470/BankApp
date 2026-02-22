package com.example.bankapp.customer.service;

import com.example.bankapp.auth.entity.User;
import com.example.bankapp.auth.repo.RoleRepository;
import com.example.bankapp.auth.repo.UserRepository;
import com.example.bankapp.customer.entity.CustomerProfile;
import com.example.bankapp.customer.entity.KycDocument;
import com.example.bankapp.customer.entity.KycStatus;
import com.example.bankapp.customer.repo.CustomerProfileRepository;
import com.example.bankapp.customer.repo.KycDocumentRepository;
import com.example.bankapp.customer.web.dto.RegisterForm;
import com.example.bankapp.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.*;
import java.util.UUID;

@Service
public class RegistrationService {

  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final CustomerProfileRepository profileRepo;
  private final KycDocumentRepository kycRepo;
  private final PasswordEncoder encoder;

  @Value("${app.upload.dir:uploads}")
  private String uploadDir;

  public RegistrationService(UserRepository userRepo, RoleRepository roleRepo,
                             CustomerProfileRepository profileRepo, KycDocumentRepository kycRepo,
                             PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.roleRepo = roleRepo;
    this.profileRepo = profileRepo;
    this.kycRepo = kycRepo;
    this.encoder = encoder;
  }

  @Transactional
  public void register(RegisterForm form) {
    if (userRepo.existsByUsername(form.getEmail())) {
      throw new BusinessException("Email already registered");
    }

    var customerRole = roleRepo.findByName("CUSTOMER")
      .orElseThrow(() -> new BusinessException("Role CUSTOMER missing"));

    User user = new User();
    user.setUsername(form.getEmail());
    user.setPasswordHash(encoder.encode(form.getPassword()));
    user.setEnabled(false); // cannot login until admin approves
    user.getRoles().add(customerRole);
    userRepo.save(user);

    CustomerProfile profile = new CustomerProfile();
    profile.setUser(user);
    profile.setFullName(form.getFullName());
    profile.setPhone(form.getPhone());
    profile.setAddress(form.getAddress());
    profile.setKycStatus(KycStatus.PENDING);
    profileRepo.save(profile);

    try {
      Files.createDirectories(Paths.get(uploadDir));
      String fileName = UUID.randomUUID() + "_" + form.getKycFile().getOriginalFilename();
      Path dest = Paths.get(uploadDir).resolve(fileName);
      form.getKycFile().transferTo(dest);

      KycDocument doc = new KycDocument();
      doc.setCustomer(profile);
      doc.setDocType(form.getDocType());
      doc.setFilePath(dest.toString());
      doc.setStatus("PENDING");
      kycRepo.save(doc);

    } catch (Exception e) {
      throw new BusinessException("KYC upload failed: " + e.getMessage());
    }
  }
}