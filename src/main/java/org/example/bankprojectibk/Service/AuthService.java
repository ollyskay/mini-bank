package org.example.bankprojectibk.Service;

import org.example.bankprojectibk.dto.request.LoginRequest;
import org.example.bankprojectibk.dto.request.UserRequest;
import org.example.bankprojectibk.dto.response.AuthResponse;
import org.example.bankprojectibk.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(UserRequest req);
    AuthResponse login(LoginRequest req);
}


