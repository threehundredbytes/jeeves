package ru.threehundredbytes.jeeves.command.system;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.boot.info.BuildProperties;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

import java.awt.*;

@DiscordCommand(key = "build", group = CommandGroup.SYSTEM)
@RequiredArgsConstructor
public class BuildCommand extends Command {
    private final BuildProperties buildProperties;

    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        User botOwner = botContext.getBotOwner();

        MessageEmbed messageEmbed = new EmbedBuilder()
                .setTitle("Bot application build information")
                .setDescription(buildProperties.getGroup() + "." + buildProperties.getArtifact())
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .addField("Version", buildProperties.getVersion(), false)
                .addField("Build timestamp", buildProperties.getTime().toString(), false)
                .setFooter("Developed by " + botOwner.getAsTag(), botOwner.getAvatarUrl())
                .setColor(new Color(29, 78, 216))
                .build();

        event.getChannel().sendMessageEmbeds(messageEmbed).queue();
    }
}
