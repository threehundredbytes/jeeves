package ru.threehundredbytes.jeeves.command.system;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

@DiscordCommand(key = "guilds", group = CommandGroup.SYSTEM)
public class GuildsCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event) {
        String title = "Bot's guild list";
        String description = "List of guilds the bot has access to";
        EmbedBuilder embedBuilder = messageService.getMessageEmbedBuilder(title, description)
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

        for (Guild guild : event.getJDA().getGuilds()) {
            int memberCount = guild.getMemberCount();
            int textChannelCount = guild.getTextChannels().size();
            int voiceChannelCount = guild.getVoiceChannels().size();

            title = String.format("%s (%s)", guild.getName(), guild.getId());
            description = String.format("%d members, %d text channels, %d voice channels", memberCount,
                    textChannelCount, voiceChannelCount);

            embedBuilder.addField(title, description, false);
        }

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}