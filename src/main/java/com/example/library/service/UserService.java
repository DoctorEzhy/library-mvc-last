package com.example.library.service;

import com.example.library.entity.UserEntity;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public boolean usernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public UserEntity register(String username, String rawPassword, String role) {
        UserEntity user = new UserEntity(username.trim(), passwordEncoder.encode(rawPassword), role);
        return userRepository.save(user);
    }
}
