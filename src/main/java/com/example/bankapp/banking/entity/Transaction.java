package com.example.bankapp.banking.entity;

import com.example.bankapp.auth.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "txn_type", nullable = false, length = 30)
  private TxnType txnType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private TxnStatus status;

  @Column(nullable = false, unique = true, length = 40)
  private String reference;

  @Column(length = 255)
  private String narrative;

  @ManyToOne(optional = false)
  @JoinColumn(name = "initiated_by", nullable = false)
  private User initiatedBy;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public TxnType getTxnType() { return txnType; }
  public void setTxnType(TxnType txnType) { this.txnType = txnType; }

  public TxnStatus getStatus() { return status; }
  public void setStatus(TxnStatus status) { this.status = status; }

  public String getReference() { return reference; }
  public void setReference(String reference) { this.reference = reference; }

  public String getNarrative() { return narrative; }
  public void setNarrative(String narrative) { this.narrative = narrative; }

  public User getInitiatedBy() { return initiatedBy; }
  public void setInitiatedBy(User initiatedBy) { this.initiatedBy = initiatedBy; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}