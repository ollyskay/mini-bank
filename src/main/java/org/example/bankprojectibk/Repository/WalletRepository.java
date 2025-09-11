package org.example.bankprojectibk.Repository;

import org.example.bankprojectibk.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    boolean existsByAccountIdAndCurrency(Long accountId, String currency);
    List<Wallet> findAllByAccountId(Long accountId);
    List<Wallet> findAllByAccountUserId(Long userId);
    Optional<Wallet> findByIdAndAccountUserId(Long id, Long userId);}
