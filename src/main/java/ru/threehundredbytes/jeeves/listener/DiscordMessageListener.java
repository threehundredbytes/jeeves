package ru.threehundredbytes.jeeves.listener;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor
public class DiscordMessageListener extends ListenerAdapter {
    private final BuildProperties buildProperties;

    @Value("${jeeves.discord.owner.id}")
    private long botOwnerId;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        if (message.getContentDisplay().startsWith("!ping")) {
            message.reply("Pong!").queue();
            message.addReaction(Emoji.fromUnicode("\uD83C\uDFD3")).queue();
        }

        if (message.getContentDisplay().startsWith("!build")) {
            if (event.getAuthor().getIdLong() != botOwnerId) {
                return;
            }

            User botOwner = event.getJDA().retrieveUserById(botOwnerId).complete();

            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setTitle("Bot application build information")
                    .setDescription(buildProperties.getGroup() + "." + buildProperties.getArtifact())
                    .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                    .addField("Version", buildProperties.getVersion(), false)
                    .addField("Build timestamp", buildProperties.getTime().toString(), false)
                    .setFooter("Developed by " + botOwner.getAsTag(), botOwner.getAvatarUrl())
                    .setColor(new Color(29, 78, 216))
                    .build();

            message.getChannel().sendMessageEmbeds(messageEmbed).queue();
        }

        if (message.getContentDisplay().startsWith("!shutdown")) {
            if (event.getAuthor().getIdLong() != botOwnerId) {
                return;
            }

            message.reply("Shutting down...").queue();
            event.getJDA().shutdown();
        }
    }
}
