package org.example.bankprojectibk.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter @Setter
public class Account extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;                    // owner

    @Column(length = 64)
    private String name;                  // optional label: "Main", "Savings"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AccountStatus status = AccountStatus.ACTIVE;
}

