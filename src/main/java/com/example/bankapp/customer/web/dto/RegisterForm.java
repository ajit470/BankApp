package com.example.bankapp.customer.web.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class RegisterForm {

  @NotBlank @Email
  private String email;

  @NotBlank @Size(min=6, max=30)
  private String password;

  @NotBlank
  private String fullName;

  @NotBlank
  private String phone;

  @NotBlank
  private String address;

  @NotBlank
  private String docType;

  @NotNull
  private MultipartFile kycFile;

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }

  public String getDocType() { return docType; }
  public void setDocType(String docType) { this.docType = docType; }

  public MultipartFile getKycFile() { return kycFile; }
  public void setKycFile(MultipartFile kycFile) { this.kycFile = kycFile; }
}