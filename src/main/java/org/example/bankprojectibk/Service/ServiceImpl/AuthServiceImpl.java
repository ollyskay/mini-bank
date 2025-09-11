package org.example.bankprojectibk.Service.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.example.bankprojectibk.Config.JwtUtil;
import org.example.bankprojectibk.Exception.EmailAlreadyExistsException;
import org.example.bankprojectibk.Model.RoleEnum;
import org.example.bankprojectibk.dto.request.LoginRequest;
import org.example.bankprojectibk.dto.request.UserRequest;
import org.example.bankprojectibk.dto.response.AuthResponse;
import org.example.bankprojectibk.Mapper.UserMapper;
import org.example.bankprojectibk.Model.User;
import org.example.bankprojectibk.Repository.UserRepository;
import org.example.bankprojectibk.Service.AuthService;
import org.example.bankprojectibk.dto.response.RegisterResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;
    private final JwtUtil jwt;


    public AuthServiceImpl(UserRepository users,
                           PasswordEncoder encoder,
                           UserMapper mapper,
                           JwtUtil jwt) {


        this.users = users;
        this.encoder = encoder;
        this.mapper = mapper;
        this.jwt = jwt;
    }

    @Override
    public RegisterResponse register(UserRequest req) {
        if (users.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }


        // find all roles requested by user
        Set<RoleEnum> selectedRoles = defaultIfEmpty(req.getRoles());


        // create and save user with requested roles
        User newUser = mapper.toNewEntity(req, selectedRoles);
        User saved = users.save(newUser);

        Set<String> roleNames = saved.getRoles().stream()
                .map(r -> r.name())
                .collect(Collectors.toSet());


        return new RegisterResponse(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail(), roleNames);


    }

    private Set<RoleEnum> defaultIfEmpty(Set<RoleEnum> incoming) {
        return (incoming == null || incoming.isEmpty())
                ? Set.of(RoleEnum.ROLE_USER)
                : incoming;
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        User u = users.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Credentials"));

        Map<String, Object> claims = Map.of("userId", u.getId(),
                "role", u.getRoles().stream().map(Enum::name).toList());

        String token = jwt.generateToken(u.getEmail(), claims);
        return new AuthResponse(u.getId(), u.getEmail(), token, u.getRoles());



    }}




