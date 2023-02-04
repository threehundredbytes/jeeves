package ru.threehundredbytes.jeeves.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.threehundredbytes.jeeves.service.CommandService;

@Component
public class DiscordMessageListener extends ListenerAdapter {
    private final CommandService commandService;

    public DiscordMessageListener(@Lazy CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        commandService.onMessageReceived(event);
    }
}
