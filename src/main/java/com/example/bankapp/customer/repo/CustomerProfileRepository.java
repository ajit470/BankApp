package com.example.bankapp.customer.repo;

import com.example.bankapp.customer.entity.CustomerProfile;
import com.example.bankapp.customer.entity.KycStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
  Optional<CustomerProfile> findByUserId(Long userId);
  List<CustomerProfile> findByKycStatus(KycStatus status);
}
