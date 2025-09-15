package org.example.bankprojectibk.Service;

import org.example.bankprojectibk.Model.User;

public interface AuthFlowService {
    void sendVerifyLink(User user);
    void verify(String rawToken);
    void forgotPassword(String email);
    void resetPassword(String rawToken, String newPassword);
    void changePassword(Long userId, String oldPassword, String newPassword);
}

