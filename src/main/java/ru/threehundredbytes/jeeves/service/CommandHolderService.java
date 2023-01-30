package ru.threehundredbytes.jeeves.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.DiscordCommand;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandHolderService {
    private final List<Command> commands;
    private final Map<String, Command> mappedCommands = new HashMap<>();
    private final Map<String, DiscordCommand> mappedDiscordCommands = new HashMap<>();

    @PostConstruct
    private void init() {
        for (Command command : commands) {
            DiscordCommand discordCommand = command.getClass().getAnnotation(DiscordCommand.class);

            if (discordCommand != null) {
                mappedCommands.put(discordCommand.key(), command);
                mappedDiscordCommands.put(discordCommand.key(), discordCommand);
            }
        }
    }

    public Command getCommandByKey(String key) {
        return mappedCommands.get(key);
    }

    public DiscordCommand getDiscordCommandByKey(String key) {
        return mappedDiscordCommands.get(key);
    }
}
