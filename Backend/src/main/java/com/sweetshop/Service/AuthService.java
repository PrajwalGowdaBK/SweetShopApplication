package com.sweetshop.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sweetshop.Model.Role;
import com.sweetshop.Model.User;
import com.sweetshop.Repository.UserRepository;
import com.sweetshop.dto.AuthRequest;
import com.sweetshop.dto.AuthResponse;
import com.sweetshop.dto.RegisterRequest;
import com.sweetshop.Security.JwtUtils;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        userRepository.save(user);

        String token = jwtUtils.generateToken(
        	    user.getUsername(),
        	    user.getRoles().stream()
        	        .map(Role::name)
        	        .collect(Collectors.toList())
        	);


        List<String> roles = user.getRoles().stream().map(Role::name).collect(Collectors.toList());
        return new AuthResponse(token, user.getUsername(), roles);
    }

    public AuthResponse login(AuthRequest req) {
        var userOpt = userRepository.findByUsername(req.getUsernameOrEmail());
        if (userOpt.isEmpty()) userOpt = userRepository.findByEmail(req.getUsernameOrEmail());
        var user = userOpt.orElseThrow(() -> new IllegalArgumentException("invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("invalid credentials");
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::name)
                .toList();   // Java 21 ✅

        String token = jwtUtils.generateToken(user.getUsername(), roles);

        return new AuthResponse(token, user.getUsername(), roles);

    }
}
