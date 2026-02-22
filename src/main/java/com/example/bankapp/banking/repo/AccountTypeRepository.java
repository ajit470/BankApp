package com.example.bankapp.banking.repo;

import com.example.bankapp.banking.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
  Optional<AccountType> findByCode(String code);
}