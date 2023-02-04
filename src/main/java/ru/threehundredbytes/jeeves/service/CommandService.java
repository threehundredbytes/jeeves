package ru.threehundredbytes.jeeves.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.CommandSource;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;
import ru.threehundredbytes.jeeves.persistence.entity.GuildConfiguration;
import ru.threehundredbytes.jeeves.utils.ArrayUtils;

import java.util.Locale;
import java.util.Map;

import static ru.threehundredbytes.jeeves.command.CommandGroup.*;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final CommandHolderService commandHolderService;
    private final BotOwnerService botOwnerService;
    private final GuildConfigurationService guildConfigurationService;

    private final Map<String, Locale> supportedLocales;

    @Value("${jeeves.prefix.default}")
    private String defaultPrefix;

    @Value("${jeeves.locale.default}")
    private String defaultLocale;

    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        Member member = event.getMember();
        User user = event.getAuthor();

        String prefix = defaultPrefix;
        Locale locale = supportedLocales.get(defaultLocale);

        if (message.isFromGuild()) {
            GuildConfiguration guildConfiguration = guildConfigurationService.getGuildConfiguration(event.getGuild());
            prefix = guildConfiguration.getPrefix();
            locale = supportedLocales.get(guildConfiguration.getLocale());
        }

        if (user.isBot() || !message.getContentDisplay().startsWith(prefix)) {
            return;
        }

        String commandKey = message.getContentDisplay().split("\\s")[0].substring(prefix.length());
        Command command = commandHolderService.getCommandByLocalizedKey(commandKey, locale);

        if (command != null) {
            DiscordCommand discordCommand = command.getClass().getAnnotation(DiscordCommand.class);

            if (!isCommandApplicable(discordCommand, message, member, user)) {
                return;
            }

            BotContext botContext = new BotContext(locale);
            command.execute(event, botContext);
        }
    }

    private boolean isCommandApplicable(DiscordCommand discordCommand, Message message, Member member, User user) {
        CommandGroup commandGroup = discordCommand.group();
        CommandSource[] commandSources = discordCommand.source();

        if (message.isFromType(ChannelType.PRIVATE)) {
            if (!ArrayUtils.contains(commandSources, CommandSource.DIRECT_MESSAGE)) {
                return false;
            }
        }

        if (message.isFromGuild()) {
            if (!ArrayUtils.contains(commandSources, CommandSource.GUILD_MESSAGE)) {
                return false;
            }

            if (MODERATION.equals(commandGroup) || CONFIGURATION.equals(commandGroup)) {
                if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                    return false;
                }
            }
        }

        if (SYSTEM.equals(commandGroup) && !user.equals(botOwnerService.getBotOwner())) {
            return false;
        }

        return true;
    }
}
