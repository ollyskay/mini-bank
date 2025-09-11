package org.example.bankprojectibk.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor

public class LoginRequest {

    private String name;
    private String password;
    private String email;

}
