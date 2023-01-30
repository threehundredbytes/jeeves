package ru.threehundredbytes.jeeves.command.system;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

@DiscordCommand(key = "shutdown", group = CommandGroup.SYSTEM)
public class ShutdownCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        event.getMessage().reply("Shutting down...").queue();
        event.getJDA().shutdown();
    }
}
