package ru.threehundredbytes.jeeves.command.utility;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

@DiscordCommand(key = "ping")
public class PingCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();

        message.reply("Pong!").queue();
        message.addReaction(Emoji.fromUnicode("\uD83C\uDFD3")).queue();
    }
}
