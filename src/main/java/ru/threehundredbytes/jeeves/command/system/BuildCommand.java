package ru.threehundredbytes.jeeves.command.system;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.boot.info.BuildProperties;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

@DiscordCommand(key = "build", group = CommandGroup.SYSTEM)
@RequiredArgsConstructor
public class BuildCommand extends Command {
    private final BuildProperties buildProperties;

    @Override
    public void execute(MessageReceivedEvent event) {
        String title = "Bot application build information";
        String description = buildProperties.getGroup() + "." + buildProperties.getArtifact();

        MessageEmbed messageEmbed = messageService.getMessageEmbedBuilder(title, description)
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .addField("Version", buildProperties.getVersion(), false)
                .addField("Build timestamp", buildProperties.getTime().toString(), false)
                .build();

        event.getChannel().sendMessageEmbeds(messageEmbed).queue();
    }
}
