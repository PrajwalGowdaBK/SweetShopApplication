package com.sweetshop.Controller;

import com.sweetshop.Model.User;
import com.sweetshop.Model.Role;
import com.sweetshop.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Response DTO to avoid returning passwordHash
    public record UserResponse(Integer id, String username, String email, Set<Role> roles) {
        public static UserResponse of(User u) {
            return new UserResponse(
                    u.getId(),
                    u.getUsername(),
                    u.getEmail(),
                    u.getRoles()
            );
        }
    }

    /**
     * Get current logged in user
     */
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<User> user = userRepository.findByUsername(auth.getName());
        if (user.isEmpty()) return ResponseEntity.status(404).body("User not found");

        return ResponseEntity.ok(UserResponse.of(user.get()));
    }

    /**
     * List all users (Admin only)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> allUsers() {
        List<UserResponse> list = userRepository.findAll()
                .stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    /**
     * Get user by ID (Admin only)
     */

    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {

        var user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(UserResponse.of(user.get()));
    }


    /**
     * Delete user by ID (Admin only)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(404).body("User not found");
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

