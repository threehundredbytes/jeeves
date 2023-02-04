package ru.threehundredbytes.jeeves.command.system;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.boot.info.BuildProperties;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

import java.util.Locale;

@DiscordCommand(key = "command.system.build.key", group = CommandGroup.SYSTEM)
@RequiredArgsConstructor
public class BuildCommand extends Command {
    private final BuildProperties buildProperties;

    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        Locale locale = botContext.locale();

        String title = messageService.getMessage("command.system.build.title", locale);
        String description = buildProperties.getGroup() + "." + buildProperties.getArtifact();

        MessageEmbed messageEmbed = messageService.getMessageEmbedBuilder(title, description)
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .addField(getVersionField(locale))
                .addField(getTimestampField(locale))
                .build();

        event.getChannel().sendMessageEmbeds(messageEmbed).queue();
    }

    private MessageEmbed.Field getVersionField(Locale locale) {
        String title = messageService.getMessage("command.system.build.field.version.title", locale);
        String description = buildProperties.getVersion();

        return new MessageEmbed.Field(title, description, false);
    }

    private MessageEmbed.Field getTimestampField(Locale locale) {
        String title = messageService.getMessage("command.system.build.field.timestamp.title", locale);
        String description = buildProperties.getTime().toString();

        return new MessageEmbed.Field(title, description, false);
    }
}
