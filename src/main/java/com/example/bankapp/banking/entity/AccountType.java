package com.example.bankapp.banking.entity;

import jakarta.persistence.*;

@Entity
@Table(name="account_types")
public class AccountType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true, length=30)
  private String code;

  @Column(name="display_name", nullable=false, length=80)
  private String displayName;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }

  public String getDisplayName() { return displayName; }
  public void setDisplayName(String displayName) { this.displayName = displayName; }
}