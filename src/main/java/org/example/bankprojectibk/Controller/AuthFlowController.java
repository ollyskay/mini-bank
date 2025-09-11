package org.example.bankprojectibk.Controller;



import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.bankprojectibk.Service.AuthFlowService;
import org.example.bankprojectibk.auth.CustomUserDetails; // adjust package if different
import org.example.bankprojectibk.Repository.UserRepository;
import org.example.bankprojectibk.Service.MailService; // adjust package if different
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    @Validated
    public class AuthFlowController {

        private final AuthFlowService flows;
        private final UserRepository users;

        // --- Email verification ---

        @PostMapping("/resend-verification")
        public ResponseEntity<?> resendVerification(@Valid @RequestBody ResendVerificationRequest req) {
            users.findByEmail(req.email()).ifPresent(flows::sendVerifyLink);
            return ResponseEntity.accepted().build(); // 202, avoids email enumeration
        }

        @GetMapping("/verify")
        public ResponseEntity<?> verify(@RequestParam("token") String token) {
            flows.verify(token);
            return ResponseEntity.ok(Map.of("message", "Email verified"));
        }

        // --- Forgot / Reset password ---

        @PostMapping("/forgot-password")
        public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
            flows.forgotPassword(req.email());
            return ResponseEntity.accepted().build(); // 202
        }

        @PostMapping("/reset-password")
        public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
            flows.resetPassword(req.token(), req.newPassword());
            return ResponseEntity.ok(Map.of("message", "Password reset"));
        }

        // --- Change password (logged-in user) ---

        @PostMapping("/change-password")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<?> changePassword(@AuthenticationPrincipal CustomUserDetails me,
                                                @Valid @RequestBody ChangePasswordRequest req) {
            flows.changePassword(me.getUser().getId(), req.oldPassword(), req.newPassword());
            return ResponseEntity.ok(Map.of("message", "Password changed"));
        }

        // --- DTOs ---

        public record ResendVerificationRequest(
                @NotBlank @Email String email
        ) {}

        public record ForgotPasswordRequest(
                @NotBlank @Email String email
        ) {}

        public record ResetPasswordRequest(
                @NotBlank String token,
                @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String newPassword
        ) {}

        public record ChangePasswordRequest(
                @NotBlank String oldPassword,
                @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String newPassword
        ) {}
    }


