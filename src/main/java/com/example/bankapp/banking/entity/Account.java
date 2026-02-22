package com.example.bankapp.banking.entity;

import com.example.bankapp.customer.entity.CustomerProfile;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  @JoinColumn(name="customer_id", nullable=false)
  private CustomerProfile customer;

  @Column(name="account_no", nullable=false, unique=true, length=20)
  private String accountNo;

  @ManyToOne(optional=false)
  @JoinColumn(name="account_type_id", nullable=false)
  private AccountType accountType;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false, length=30)
  private AccountStatus status = AccountStatus.ACTIVE;

  @Column(nullable=false, precision=15, scale=2)
  private BigDecimal balance = BigDecimal.ZERO;

  @Column(name="opened_at", nullable=false)
  private LocalDateTime openedAt = LocalDateTime.now();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public CustomerProfile getCustomer() { return customer; }
  public void setCustomer(CustomerProfile customer) { this.customer = customer; }

  public String getAccountNo() { return accountNo; }
  public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

  public AccountType getAccountType() { return accountType; }
  public void setAccountType(AccountType accountType) { this.accountType = accountType; }

  public AccountStatus getStatus() { return status; }
  public void setStatus(AccountStatus status) { this.status = status; }

  public BigDecimal getBalance() { return balance; }
  public void setBalance(BigDecimal balance) { this.balance = balance; }

  public LocalDateTime getOpenedAt() { return openedAt; }
  public void setOpenedAt(LocalDateTime openedAt) { this.openedAt = openedAt; }
}
