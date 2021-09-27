package com.example.securingweb.bot;

import com.example.securingweb.domain.TelegramChat;
import com.example.securingweb.repo.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {
    private final String PASSWORD = "/addMeToMailing";

    @Autowired
    private ChatRepo chatRepo;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            String text = message.getText();
            String chatId = String.valueOf(message.getChatId());

            if (text.equals(PASSWORD)) {

                if (chatRepo.findTelegramChatByChatId(chatId) == null) {
                    TelegramChat chat = new TelegramChat();
                    chat.setChatId(chatId);
                    chatRepo.save(chat);

                    String welcome = "Welcome to GGame Cash Session mailing";
                    SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(welcome).build();
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (text.equals("/stop")) {
                String welcome = "Deleted from GGame Cash Session mailing";
                SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(welcome).build();
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                TelegramChat chat = chatRepo.findTelegramChatByChatId(chatId);
                if (chat != null) {
                    chatRepo.delete(chat);
                }
            }
        }
    }
}
