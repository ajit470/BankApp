package com.example.bankapp.banking.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class MoneyForm {

  @NotNull
  private Long accountId;

  @NotNull
  @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
  private BigDecimal amount;

  public Long getAccountId() { return accountId; }
  public void setAccountId(Long accountId) { this.accountId = accountId; }

  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
}