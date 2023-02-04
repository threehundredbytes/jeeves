package ru.threehundredbytes.jeeves.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BotOwnerService {
    private final JDA jda;
    private User botOwner;

    @Value("${jeeves.discord.owner.id}")
    private long botOwnerId;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    private void retrieveBotOwner() {
        jda.retrieveUserById(botOwnerId).queue(user -> botOwner = user);
    }

    public User getBotOwner() {
        return botOwner;
    }
}