package com.example.securingweb.service;

import com.example.securingweb.domain.Session;
import com.example.securingweb.domain.User;
import com.example.securingweb.repo.SessionRepo;
import com.example.securingweb.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.StreamSupport;

@Service
public class EmptySessionCreateService {

    private final UserRepo userRepo;
    private final SessionRepo sessionRepo;

    public EmptySessionCreateService(SessionRepo sessionRepo, UserRepo userRepo) {
        this.sessionRepo = sessionRepo;
        this.userRepo = userRepo;
    }

    public void createEmptySession() {
        long count = StreamSupport.stream(sessionRepo.findAll().spliterator(), false).count();

        if (count == 0) {
            Session emptySession = new Session();

            User user = userRepo.findByUsername("rootAdmin");
            emptySession.setUserName(user.getUsername());
            emptySession.setStartAmount(0);
            emptySession.setDate(new Date());
            emptySession.setChecked(false);
            emptySession.setNote("");
            emptySession.setCollection(0);
            emptySession.setCash(0);
            emptySession.setCashReturn(0);
            emptySession.setNonCash(0);
            emptySession.setNonCashReturn(0);
            emptySession.setCleaning(0);
            emptySession.setBonus(0);
            emptySession.setTerminal(0);
            emptySession.setEndAmount(0);

        sessionRepo.save(emptySession);
        }
    }
}
