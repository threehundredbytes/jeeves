package ru.threehundredbytes.jeeves.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import ru.threehundredbytes.jeeves.model.BotContext;
import ru.threehundredbytes.jeeves.service.MessageService;

public abstract class Command {
    @Autowired
    protected MessageService messageService;

    public abstract void execute(MessageReceivedEvent event, BotContext botContext);
}
