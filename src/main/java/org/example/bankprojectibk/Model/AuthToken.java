package org.example.bankprojectibk.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Optional;

@Entity
@Table(name = "auth_tokens",
        indexes = {@Index(columnList = "tokenHash"), @Index(columnList = "type"), @Index(columnList = "expiresAt")})
@Getter
@Setter
public class AuthToken extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TokenType type;

    @Column(nullable = false, unique = true, length = 64)
    private String tokenHash;

    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    private OffsetDateTime usedAt;

    @Transient
    private String rawToken;

    public boolean isUsed(){
        return usedAt != null;
    };
    public boolean isExpired() {
        return OffsetDateTime.now().isAfter(expiresAt); }


}
