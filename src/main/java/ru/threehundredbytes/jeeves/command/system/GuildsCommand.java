package ru.threehundredbytes.jeeves.command.system;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

import java.util.Locale;

@DiscordCommand(key = "command.system.guilds.key", group = CommandGroup.SYSTEM)
public class GuildsCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        Locale locale = botContext.locale();
        String title = messageService.getMessage("command.system.guilds.title", locale);
        String description = messageService.getMessage("command.system.guilds.description", locale);

        EmbedBuilder embedBuilder = messageService.getMessageEmbedBuilder(title, description)
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

        event.getJDA().getGuilds().forEach(guild -> embedBuilder.addField(getGuildField(guild, locale)));
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    private MessageEmbed.Field getGuildField(Guild guild, Locale locale) {
        int memberCount = guild.getMemberCount();
        int textChannelCount = guild.getTextChannels().size();
        int voiceChannelCount = guild.getVoiceChannels().size();

        String title = messageService.getMessage("command.system.guilds.field.guild.title", locale,
                guild.getName(), guild.getId());
        String description = messageService.getMessage("command.system.guilds.field.guild.description", locale,
                memberCount, textChannelCount, voiceChannelCount);

        return new MessageEmbed.Field(title, description, false);
    }
}