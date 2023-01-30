package ru.threehundredbytes.jeeves.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class DiscordMessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getContentDisplay().startsWith("!ping")) {
            message.reply("Pong!").queue();
            message.addReaction(Emoji.fromUnicode("\uD83C\uDFD3")).queue();
        }
    }
}
