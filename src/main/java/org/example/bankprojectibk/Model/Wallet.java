package org.example.bankprojectibk.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(
        name = "wallets",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_wallet_account_currency", columnNames = {"account_id","currency"})
        }
)

public class Wallet extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Account account;

    @Column(nullable = false, length = 3)
    private String currency;                        // ISO 4217, e.g. "USD", "NGN"

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;   // stored with scale=4

    @Version
    private Long version;                           // optimistic locking
}