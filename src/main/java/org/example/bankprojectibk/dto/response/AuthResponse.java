package org.example.bankprojectibk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bankprojectibk.Model.RoleEnum;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthResponse {

    private Long id;
    private String email;
    private String token;
    private Set<RoleEnum> roles = new HashSet<>();

}
