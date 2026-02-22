package com.example.bankapp.banking.repo;

import com.example.bankapp.banking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> { }
