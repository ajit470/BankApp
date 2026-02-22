package com.example.bankapp.customer.entity;

import jakarta.persistence.*;

@Entity
@Table(name="kyc_documents")
public class KycDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  @JoinColumn(name="customer_id", nullable=false)
  private CustomerProfile customer;

  @Column(name="doc_type", nullable=false, length=40)
  private String docType;

  @Column(name="file_path", nullable=false, length=255)
  private String filePath;

  @Column(nullable=false, length=30)
  private String status = "PENDING";

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public CustomerProfile getCustomer() { return customer; }
  public void setCustomer(CustomerProfile customer) { this.customer = customer; }

  public String getDocType() { return docType; }
  public void setDocType(String docType) { this.docType = docType; }

  public String getFilePath() { return filePath; }
  public void setFilePath(String filePath) { this.filePath = filePath; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}