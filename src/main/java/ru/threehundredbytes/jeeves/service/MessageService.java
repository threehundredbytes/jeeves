package ru.threehundredbytes.jeeves.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.SelfUser;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSource messageSource;
    private final Color primaryColor;
    private final JDA jda;

    private EmbedBuilder getBaseEmbedBuilder() {
        SelfUser selfUser = jda.getSelfUser();
        return new EmbedBuilder()
                .setColor(primaryColor)
                .setTimestamp(Instant.now())
                .setFooter(selfUser.getAsTag(), selfUser.getAvatarUrl());
    }

    public EmbedBuilder getMessageEmbedBuilder(String title, String description) {
        return getBaseEmbedBuilder()
                .setTitle(title)
                .setDescription(description);
    }

    public String getMessage(String key, Locale locale, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }
}