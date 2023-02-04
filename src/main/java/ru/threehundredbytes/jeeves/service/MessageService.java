package ru.threehundredbytes.jeeves.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final Color primaryColor;
    private final JDA jda;

    private EmbedBuilder getBaseEmbedBuilder() {
        return new EmbedBuilder()
                .setColor(primaryColor)
                .setTimestamp(Instant.now())
                .setFooter("Jeeves Bot", jda.getSelfUser().getAvatarUrl());
    }

    public EmbedBuilder getMessageEmbedBuilder(String title, String description) {
        return getBaseEmbedBuilder()
                .setTitle(title)
                .setDescription(description);
    }
}