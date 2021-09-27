package com.example.securingweb.repo;

import com.example.securingweb.domain.TelegramChat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepo extends CrudRepository<TelegramChat, Long> {
    TelegramChat findTelegramChatByChatId(String chatId);
}
