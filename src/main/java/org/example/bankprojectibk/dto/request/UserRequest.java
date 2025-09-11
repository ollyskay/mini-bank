package org.example.bankprojectibk.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bankprojectibk.Model.RoleEnum;

import java.util.HashSet;
import java.util.Set;
@Data
@Getter
@Setter
public class UserRequest {
    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    @NotBlank
    private String password;

    private String firstName;
    private String lastName;
    private Set<RoleEnum> roles; // may be null or empty

}
