package org.example.bankprojectibk.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.example.bankprojectibk.Model.User;
import org.example.bankprojectibk.Model.AuthToken;
import org.example.bankprojectibk.Model.TokenType;
import org.example.bankprojectibk.Repository.AuthTokenRepository;
import org.example.bankprojectibk.Repository.UserRepository;
import org.example.bankprojectibk.Service.AuthFlowService;
import org.example.bankprojectibk.Service.MailService;
import org.example.bankprojectibk.Util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthFlowServiceImpl implements AuthFlowService {

    private final UserRepository users;
    private final AuthTokenRepository tokens;
    private final PasswordEncoder encoder;
    private final MailService mail;
    private final TokenUtil tokenUtil;

    @Value("${app.frontend.base-url:http://localhost:5173}")
    private String baseUrl;

    // ===== Email Verification =====

    @Override
    @Transactional
    public void sendVerifyLink(User user) {
        // issue new token; optionally invalidate older VERIFY_EMAIL tokens for this user
        AuthToken t = issueToken(user, TokenType.VERIFY_EMAIL, 24 * 60); // minutes
        String link = baseUrl + "/verify?token=" + t.getRawToken();      // raw token only in email
        mail.sendHtml(
                user.getEmail(),
                "Verify your email",
                "<p>Hello " + safe(user.getFirstName()) + ",</p>"
                        + "<p>Please verify your email: <a href=\"" + link + "\">Verify</a></p>"
        );
    }

    @Override
    @Transactional
    public void verify(String rawToken) {
        AuthToken t = consumeValid(rawToken, TokenType.VERIFY_EMAIL);
        User u = t.getUser();
        u.setEmailVerified(true);
        // keep token row for audit or delete it:
        t.setUsedAt(OffsetDateTime.now());
        // tokens.delete(t); // if you prefer delete-on-use
    }

    // ===== Forgot / Reset Password =====

    @Override
    @Transactional
    public void forgotPassword(String email) {
        Optional<User> ou = users.findByEmail(email);
        if (ou.isEmpty()) return; // always act like it succeeded (avoid enumeration)

        User user = ou.get();
        AuthToken t = issueToken(user, TokenType.RESET_PASSWORD, 60); // 60 minutes
        String link = baseUrl + "/reset-password?token=" + t.getRawToken();
        mail.sendHtml(
                user.getEmail(),
                "Reset your password",
                "<p>Hello " + safe(user.getFirstName()) + ",</p>"
                        + "<p>Reset your password here: <a href=\"" + link + "\">Reset</a></p>"
                        + "<p>This link expires in 60 minutes.</p>"
        );
    }

    @Override
    @Transactional
    public void resetPassword(String rawToken, String newPassword) {
        AuthToken t = consumeValid(rawToken, TokenType.RESET_PASSWORD);
        User u = t.getUser();
        u.setPassword(encoder.encode(newPassword));
        t.setUsedAt(OffsetDateTime.now());
        // tokens.delete(t); // if you prefer delete-on-use
    }

    // ===== Change Password (logged-in) =====

    @Override
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User u = users.findById(userId).orElseThrow();
        if (!encoder.matches(oldPassword, u.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        u.setPassword(encoder.encode(newPassword));
    }

    // ===== Helpers =====

    /**
     * Creates and persists a token; stores only the hash in DB.
     * Returns the entity with the raw token attached (transient).
     */
    private AuthToken issueToken(User user, TokenType type, int ttlMinutes) {
        String raw = tokenUtil.newRawToken();
        String hash = tokenUtil.sha256(raw);

        // Optionally invalidate older tokens of same type for this user:
        // tokens.deleteByUserIdAndType(user.getId(), type);  // add repo method if desired

        AuthToken t = new AuthToken();
        t.setUser(user);
        t.setType(type);
        t.setTokenHash(hash);
        t.setExpiresAt(OffsetDateTime.now().plusMinutes(ttlMinutes));
        tokens.save(t);

        // attach raw token transiently so caller can email it
        t.setRawToken(raw); // add a @Transient field rawToken in AuthToken to hold this
        return t;
    }

    /**
     * Finds a valid token by raw value and type; throws if invalid/expired/used.
     * Marks it as used (or you can delete it).
     */
    private AuthToken consumeValid(String rawToken, TokenType expectedType) {
        String hash = tokenUtil.sha256(rawToken);
        AuthToken t = tokens.findByTokenHash(hash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        if (t.getType() != expectedType) {
            throw new IllegalArgumentException("Invalid token");
        }
        if (t.getUsedAt() != null || t.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        return t;
    }

    private String safe(String s) { return s == null ? "" : s; }
}
