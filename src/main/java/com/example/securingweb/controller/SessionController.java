package com.example.securingweb.controller;

import com.example.securingweb.domain.Session;
import com.example.securingweb.repo.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/session")
@PreAuthorize("hasAuthority('ADMIN')")
public class SessionController {
    @Autowired
    private SessionRepo sessionRepo;

    @GetMapping("{session}")
    public String userEditForm(@PathVariable Session session, Model model) {
        model.addAttribute("cashsession", session);
        return "sessionEdit";
    }

    @PostMapping("{session}")
    public String sessionSave(
            @RequestParam Integer cash,
            @RequestParam Integer nonCash,
            @RequestParam Integer cashReturn,
            @RequestParam Integer nonCashReturn,
            @RequestParam Integer cleaning,
            @RequestParam Integer collection,
            @RequestParam String note,
            @RequestParam(value = "sessionId") Session cashsession) {

        cashsession.setCash(cash);
        cashsession.setNonCash(nonCash);
        cashsession.setCashReturn(cashReturn);
        cashsession.setNonCashReturn(nonCashReturn);
        cashsession.setCleaning(cleaning);
        cashsession.setCollection(collection);

        Integer endAmount = cashsession.getStartAmount() + cash - cleaning - collection;
        cashsession.setEndAmount(endAmount);

        cashsession.setNote(note);
        sessionRepo.save(cashsession);
        return "redirect:/home";
    }
}
