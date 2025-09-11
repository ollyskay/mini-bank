package org.example.bankprojectibk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterResponse {


        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Set<String> roles;

}
