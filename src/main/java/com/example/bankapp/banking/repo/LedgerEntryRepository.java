package com.example.bankapp.banking.repo;

import com.example.bankapp.banking.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {

  List<LedgerEntry> findTop20ByAccountIdOrderByCreatedAtDesc(Long accountId);

  // ✅ for admin transaction page
  List<LedgerEntry> findTop50ByAccountIdOrderByCreatedAtDesc(Long accountId);
}