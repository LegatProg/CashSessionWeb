package com.example.securingweb.controller;

import com.example.securingweb.repo.SessionRepo;
import com.example.securingweb.repo.UserRepo;
import com.example.securingweb.service.EmptySessionCreateService;
import com.example.securingweb.service.RootAdminCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        new RootAdminCreateService(userRepo, passwordEncoder).createRootAdmin();
        new EmptySessionCreateService(sessionRepo, userRepo).createEmptySession();
        return "/home";
    }
}
