package com.example.securingweb.service;

import com.example.securingweb.domain.Role;
import com.example.securingweb.domain.User;
import com.example.securingweb.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class RootAdminCreateService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public RootAdminCreateService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public void createRootAdmin() {

        if (userRepo.findAll().isEmpty()) {
            User rootAdmin = new User();
            rootAdmin.setUsername("rootAdmin");
            rootAdmin.setPassword(passwordEncoder.encode("rfhntkmfkjuj"));
            rootAdmin.setRoles(new HashSet<>(Arrays.asList(Role.values())));
            rootAdmin.setActive(true);
            userRepo.save(rootAdmin);
        }
    }
}
