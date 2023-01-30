package ru.threehundredbytes.jeeves.command.system;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Value;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

@DiscordCommand(key = "shutdown")
public class ShutdownCommand extends Command {
    @Value("${jeeves.discord.owner.id}")
    private long botOwnerId;

    @Override
    public void execute(MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != botOwnerId) {
            return;
        }

        event.getMessage().reply("Shutting down...").queue();
        event.getJDA().shutdown();
    }
}
