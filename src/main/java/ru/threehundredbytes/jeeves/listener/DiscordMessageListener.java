package ru.threehundredbytes.jeeves.listener;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscordMessageListener extends ListenerAdapter {
    private final List<Command> commands;
    private final String PREFIX = "!";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        for (Command command : commands) {
            DiscordCommand discordCommand = command.getClass().getAnnotation(DiscordCommand.class);

            if (discordCommand != null && message.getContentDisplay().startsWith(PREFIX + discordCommand.key())) {
                command.execute(event);
            }
        }
    }
}
