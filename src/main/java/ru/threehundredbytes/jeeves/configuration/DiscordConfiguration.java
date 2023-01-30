package ru.threehundredbytes.jeeves.configuration;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.threehundredbytes.jeeves.listener.DiscordMessageListener;

@Configuration
@RequiredArgsConstructor
public class DiscordConfiguration {
    @Value("${jeeves.discord.token}")
    private String token;

    private final DiscordMessageListener discordMessageListener;

    @Bean
    public JDA jda() {
        return JDABuilder.createLight(token, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.competing("development"))
                .addEventListeners(discordMessageListener)
                .build();
    }
}
