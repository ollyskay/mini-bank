package org.example.bankprojectibk.Repository;

import org.example.bankprojectibk.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
        List<Account> findAllUserById(Long userid);
        Optional<Account> findByIdAndUserId(Long id, Long userid);
    }

