package org.example.bankprojectibk.Mapper;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bankprojectibk.Model.RoleEnum;
import org.example.bankprojectibk.Model.User;
import org.example.bankprojectibk.dto.request.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Data
@Setter
@Getter
public class UserMapper {

    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User toNewEntity(UserRequest req, Set<RoleEnum>roles) {
        User u = new User();
                u.setEmail(req.getEmail());
                u.setPassword(encoder.encode(req.getPassword()));
                u.setFirstName(req.getFirstName());
                u.setLastName(req.getLastName());
                u.setRoles(roles);
                u.setActive(true);
                u.setCanLogin(true);

                return u;
    }





}
