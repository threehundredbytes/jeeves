package ru.threehundredbytes.jeeves.command.moderation;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.CommandSource;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@DiscordCommand(key = "ban", group = CommandGroup.MODERATION, source = CommandSource.GUILD_MESSAGE)
public class BanCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event) {
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
                .forEach(member -> member.ban(0, TimeUnit.SECONDS).reason(reason).queue(
                        v -> message.addReaction(Emoji.fromUnicode("✅")).queue(),
                        t -> message.addReaction(Emoji.fromUnicode("❌")).queue()
                ));
    }
}
