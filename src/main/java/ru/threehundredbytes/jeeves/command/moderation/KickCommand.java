package ru.threehundredbytes.jeeves.command.moderation;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.CommandSource;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DiscordCommand(key = "command.moderation.kick.key", group = CommandGroup.MODERATION, source = CommandSource.GUILD_MESSAGE)
public class KickCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        Message message = event.getMessage();

        List<Member> mentionedMembers = message.getMentions().getMembers();

        if (mentionedMembers.isEmpty()) {
            message.addReaction(Emoji.fromUnicode("❌")).queue();
        }

        String reason = Arrays.stream(message.getContentRaw().split("\\s+"))
                .skip(mentionedMembers.size() + 1)
                .collect(Collectors.joining(" "));

        Member selfMember = event.getGuild().getSelfMember();

        mentionedMembers.stream()
                .filter(selfMember::canInteract)
                .forEach(member -> member.kick().reason(reason).queue(
                        v -> message.addReaction(Emoji.fromUnicode("✅")).queue(),
                        t -> message.addReaction(Emoji.fromUnicode("❌")).queue()
                ));
    }
}