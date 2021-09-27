package com.example.securingweb.controller;

import com.example.securingweb.bot.Bot;
import com.example.securingweb.domain.Session;
import com.example.securingweb.domain.TelegramChat;
import com.example.securingweb.domain.User;
import com.example.securingweb.repo.ChatRepo;
import com.example.securingweb.repo.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('USER')")
public class AddSessionController {
    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ChatRepo chatRepo;

    private Session prevSession;
    private int difference;

    @GetMapping("addSessionError")
    public String addSessionError(@RequestParam String message, Map<String, Object> model) {
        model.put("difference", difference);
        model.put("message", message);
        return "addSessionError";
    }

    @GetMapping("openSession")
    public String openSession(Map<String, Object> model) {
        initPrevSession();
        if (prevSession.isChecked()) {
            String message = "Session is OPENED! Close your session!";
            return "redirect:/addSessionError?message=" + message;
        }
        model.put("startAmount", prevSession.getEndAmount());
        return "openSession";
    }

    @PostMapping("/openSession")
    public String saveSession(@AuthenticationPrincipal User user,
                              Session session,
                              Map<String, Object> model) {
        prevSession.setChecked(true);
        sessionRepo.save(prevSession);
        return "redirect:/home";
    }
//    }

    @GetMapping("/addsession")
    public String addsession(Map<String, Object> model) {
        initPrevSession();
        if (!prevSession.isChecked()) {
            String message = "Session is not OPENED! Open your session!";
            return "redirect:/addSessionError?message=" + message;
        }
        model.put("startAmount", prevSession.getEndAmount());
        return "addsession";
    }

    @PostMapping("/addsession")
    public String addSession(@AuthenticationPrincipal User user,
                             Session session,
                             Map<String, Object> model) {
        try {
            session.setStartAmount(prevSession.getEndAmount());

            difference = session.getNonCash() - session.getNonCashReturn() - session.getTerminal();
            if (difference != 0) {
                String message = "Wrong TERMINAL value. Difference = " + difference;
                return "redirect:/addSessionError?message=" + message;
            }

            Integer endAmount = session.getStartAmount() + session.getCash() - session.getCleaning() - session.getCollection();
            if (endAmount < 0) {
                String message = "cash balance is less than 0!";
                return "redirect:/addSessionError?message=" + message;
            }


            session.setDate(new Date());
            session.setUserName(user.getUsername());
            session.setEndAmount(endAmount);
            session.setChecked(false);
            sessionRepo.save(session);

            sendReport(session);

            return "redirect:/closeSession?endAmount=" + endAmount;

        } catch (NullPointerException e) {
            String message = "Empty field!";
            return "redirect:/addSessionError?message=" + message;
        }
    }

    private void initPrevSession() {
        Iterable<Session> sessions = sessionRepo.findAll();
        ArrayList<Session> sessionArrayList = new ArrayList<>();
        sessions.forEach(sessionArrayList::add);
        prevSession = sessionArrayList.get(sessionArrayList.size() - 1);
    }

    private String buildReport(Session session) {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Закрыта кассовая смена\n")
                .append("Пользователь: ").append(session.getUserName()).append("\n")
                .append("Выручка нал: ").append(session.getCash()).append("\n")
                .append("Выручка б/н: ").append(session.getNonCash()).append("\n")
                .append("Возвраты нал : ").append(session.getCashReturn()).append("\n")
                .append("Возвраты б/н: ").append(session.getNonCashReturn()).append("\n")
                .append("Клининг: ").append(session.getCleaning()).append("\n")
                .append("Инкассация: ").append(session.getCollection()).append("\n")
                .append("Остаток в кассе на конец смены: ").append(session.getEndAmount()).append("\n")
                .append("Бонус-касса: ").append(session.getBonus()).append("\n")
                .append("Примечание: ").append(session.getNote()).append("\n")
                .append("Выручка ИТОГО: ").append(session.getCash() + session.getNonCash() - session.getCashReturn() - session.getNonCashReturn());
        return reportBuilder.toString();
    }

    private void sendReport(Session session){
        String report = buildReport(session);
        Bot bot = context.getBean(Bot.class);
        Iterable<TelegramChat> telegramChatList = chatRepo.findAll();
        telegramChatList.forEach(telegramChat -> {
            try {
                bot.execute(SendMessage.builder().chatId(telegramChat.getChatId()).text(report).build());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }
}

