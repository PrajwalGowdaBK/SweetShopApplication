
package com.sweetshop.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        String plain = "admin123"; // change if you want different admin password
        System.out.println(enc.encode(plain));
    }
}
