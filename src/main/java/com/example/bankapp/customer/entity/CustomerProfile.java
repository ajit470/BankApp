package com.example.bankapp.customer.entity;

import com.example.bankapp.auth.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name="customer_profiles")
public class CustomerProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional=false)
  @JoinColumn(name="user_id", nullable=false, unique=true)
  private User user;

  @Column(name="full_name", nullable=false, length=150)
  private String fullName;

  @Column(nullable=false, length=20)
  private String phone;

  @Column(nullable=false, length=255)
  private String address;

  @Enumerated(EnumType.STRING)
  @Column(name="kyc_status", nullable=false, length=30)
  private KycStatus kycStatus = KycStatus.PENDING;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }

  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }

  public KycStatus getKycStatus() { return kycStatus; }
  public void setKycStatus(KycStatus kycStatus) { this.kycStatus = kycStatus; }
}