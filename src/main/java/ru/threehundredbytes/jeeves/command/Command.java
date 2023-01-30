package ru.threehundredbytes.jeeves.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.model.BotContext;

public abstract class Command {
    public abstract void execute(MessageReceivedEvent event, BotContext botContext);
}
