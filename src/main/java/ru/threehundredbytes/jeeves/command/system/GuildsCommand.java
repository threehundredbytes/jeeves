package ru.threehundredbytes.jeeves.command.system;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Value;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

import java.awt.*;

@DiscordCommand(key = "guilds")
public class GuildsCommand extends Command {
    @Value("${jeeves.discord.owner.id}")
    private long botOwnerId;

    @Override
    public void execute(MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != botOwnerId) {
            return;
        }

        User botOwner = event.getJDA().retrieveUserById(botOwnerId).complete();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Bot's guild list")
                .setDescription("List of guilds the bot has access to")
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .setFooter("Developed by " + botOwner.getAsTag(), botOwner.getAvatarUrl())
                .setColor(new Color(29, 78, 216));

        for (Guild guild : event.getJDA().getGuilds()) {
            String title = String.format("%s (%s)", guild.getName(), guild.getId());
            String description = String.format("%d members, %d text channels, %d voice channels",
                    guild.getMemberCount(), guild.getTextChannels().size(), guild.getVoiceChannels().size());

            embedBuilder.addField(title, description, false);
        }

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
