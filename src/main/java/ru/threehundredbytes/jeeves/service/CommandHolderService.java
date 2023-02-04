package ru.threehundredbytes.jeeves.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandHolderService {
    private final List<Command> commands;
    private final Map<Locale, Map<String, Command>> localizedCommandsMappedByLocale = new HashMap<>();

    private final MessageService messageService;
    private final Map<String, Locale> supportedLocales;

    @PostConstruct
    private void init() {
        for (Command command : commands) {
            DiscordCommand discordCommand = command.getClass().getAnnotation(DiscordCommand.class);

            if (discordCommand == null) {
                continue;
            }

            for (Locale locale : supportedLocales.values()) {
                String localizedCommandKey = messageService.getMessage(discordCommand.key(), locale);

                localizedCommandsMappedByLocale.computeIfAbsent(locale, l -> new HashMap<>())
                        .put(localizedCommandKey, command);
            }
        }
    }

    public Command getCommandByLocalizedKey(String localizedKey, Locale locale) {
        return localizedCommandsMappedByLocale.get(locale).get(localizedKey);
    }
}
