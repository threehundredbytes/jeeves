package ru.threehundredbytes.jeeves.command.configuration;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.CommandSource;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;
import ru.threehundredbytes.jeeves.service.GuildConfigurationService;

@DiscordCommand(key = "command.configuration.locale.key", group = CommandGroup.CONFIGURATION, source = CommandSource.GUILD_MESSAGE)
@RequiredArgsConstructor
public class LocaleCommand extends Command {
    private final GuildConfigurationService guildConfigurationService;

    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        String locale = event.getMessage().getContentDisplay().trim().split("\\s")[1];
        guildConfigurationService.setLocale(locale, event.getGuild());

        event.getMessage().addReaction(Emoji.fromUnicode("✅")).queue();
    }
}
