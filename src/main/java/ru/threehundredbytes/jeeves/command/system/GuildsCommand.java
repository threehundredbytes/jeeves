package ru.threehundredbytes.jeeves.command.system;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

import java.awt.*;

@DiscordCommand(key = "guilds", group = CommandGroup.SYSTEM)
@RequiredArgsConstructor
public class GuildsCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        User botOwner = botContext.getBotOwner();

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
