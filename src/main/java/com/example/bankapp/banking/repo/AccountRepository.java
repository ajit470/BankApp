package com.example.bankapp.banking.repo;

import com.example.bankapp.banking.entity.Account;
import com.example.bankapp.customer.entity.CustomerProfile;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findByCustomer(CustomerProfile customer);

  // ✅ for admin: list accounts by customerId
  List<Account> findByCustomerId(Long customerId);

  Optional<Account> findByIdAndCustomerId(Long id, Long customerId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select a from Account a where a.id = :accountId and a.customer.id = :customerId")
  Optional<Account> lockByIdAndCustomerId(@Param("accountId") Long accountId,
                                         @Param("customerId") Long customerId);
}