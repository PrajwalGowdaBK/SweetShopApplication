package com.sweetshop.config;

import com.sweetshop.Model.Role;
import com.sweetshop.Model.User;
import com.sweetshop.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123")); // change if needed
            admin.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
            userRepository.save(admin);
            System.out.println("Created default admin / password: admin123");
        }
    }
}
