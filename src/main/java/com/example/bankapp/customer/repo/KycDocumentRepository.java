package com.example.bankapp.customer.repo;

import com.example.bankapp.customer.entity.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycDocumentRepository extends JpaRepository<KycDocument, Long> { }
