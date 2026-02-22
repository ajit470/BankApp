package com.example.bankapp.admin.web.dto;

import java.time.LocalDateTime;

public class AdminUserRow {

  private Long userId;
  private Long customerId;     // ✅ important for "View Accounts"
  private String username;
  private String roles;
  private boolean enabled;
  private boolean locked;
  private LocalDateTime createdAt;

  private String fullName;
  private String phone;
  private String kycStatus;

  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }

  public Long getCustomerId() { return customerId; }
  public void setCustomerId(Long customerId) { this.customerId = customerId; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getRoles() { return roles; }
  public void setRoles(String roles) { this.roles = roles; }

  public boolean isEnabled() { return enabled; }
  public void setEnabled(boolean enabled) { this.enabled = enabled; }

  public boolean isLocked() { return locked; }
  public void setLocked(boolean locked) { this.locked = locked; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getKycStatus() { return kycStatus; }
  public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }
}