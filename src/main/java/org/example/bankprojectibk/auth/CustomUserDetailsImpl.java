//package org.example.bankprojectibk.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.example.bankprojectibk.Model.Role;
//import org.example.bankprojectibk.Model.User;
//import org.example.bankprojectibk.Repository.UserRepository;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + username));
//
//        if (!user.getCanLogin()) {
//            throw new DisabledException("Your account has been disabled. Contact an administrator.");
//        }
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : user.getRoles()) {
//
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//
//            Optional.ofNullable(role.getPermissions())
//                    .orElseGet(Collections::emptySet)
//                    .stream()
//                    .map(permission -> new SimpleGrantedAuthority(permission.getName()))
//                    .forEach(authorities::add);
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                user.isActive(),
//                true,
//                true,
//                true,
//                authorities
//        );
//
//
//    }
//
//}
