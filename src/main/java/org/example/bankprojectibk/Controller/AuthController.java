package org.example.bankprojectibk.Controller;

import jakarta.validation.Valid;
import org.example.bankprojectibk.Service.AuthService;
import org.example.bankprojectibk.dto.request.LoginRequest;
import org.example.bankprojectibk.dto.request.UserRequest;
import org.example.bankprojectibk.dto.response.ApiResponse;
import org.example.bankprojectibk.dto.response.AuthResponse;
import org.example.bankprojectibk.dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {this.auth = auth;}

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register( @RequestBody UserRequest req) {
        RegisterResponse data = auth.register(req);
        return ApiResponse.success("Registration Successful", data);

    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        AuthResponse response = auth.login(req);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }


}
