package com.Portal.FeedbackPortal.controller;

import com.Portal.FeedbackPortal.config.JwtTokenProvider;
import com.Portal.FeedbackPortal.model.User;
import com.Portal.FeedbackPortal.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // yaha se UserDetails nikalega
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        // ✅ ab generateToken me userDetails pass kar
        String token = jwtTokenProvider.generateToken(userDetails);

        // role bhi authorities se nikal lo
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", role
        ));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String role = body.get("role"); // optional

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "username and password required"));
        }

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));

        // ✅ Role fix
        String fixedRole;
        if (role != null) {
            role = role.toUpperCase().replace("ROLE_", ""); // normalize
            if (role.equals("ADMIN")) {
                fixedRole = "ROLE_ADMIN";
            } else {
                fixedRole = "ROLE_USER";
            }
        } else {
            fixedRole = "ROLE_USER"; // default
        }

        u.setRoles(new HashSet<>(Collections.singletonList(fixedRole)));

        userRepository.save(u);

        return ResponseEntity.ok(Map.of("message", "User registered"));
    }
}