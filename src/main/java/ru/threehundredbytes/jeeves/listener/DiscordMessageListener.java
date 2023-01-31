package ru.threehundredbytes.jeeves.listener;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;
import ru.threehundredbytes.jeeves.service.BotOwnerService;
import ru.threehundredbytes.jeeves.service.CommandHolderService;

import static ru.threehundredbytes.jeeves.command.CommandGroup.*;

@Component
public class DiscordMessageListener extends ListenerAdapter {
    private final CommandHolderService commandHolderService;
    private final BotOwnerService botOwnerService;
    private final String PREFIX = "!";

    public DiscordMessageListener(@Lazy CommandHolderService commandHolderService, @Lazy BotOwnerService botOwnerService) {
        this.commandHolderService = commandHolderService;
        this.botOwnerService = botOwnerService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = event.getAuthor();
        Member member = event.getMember();

        if (user.isBot() || !message.getContentDisplay().startsWith(PREFIX)) {
            return;
        }

        String commandKey = message.getContentDisplay().split("\\s")[0].substring(PREFIX.length());

        Command command = commandHolderService.getCommandByKey(commandKey);
        DiscordCommand discordCommand = commandHolderService.getDiscordCommandByKey(commandKey);

        if (command != null && discordCommand != null) {
            User botOwner = botOwnerService.getBotOwner();

            CommandGroup commandGroup = discordCommand.group();

            if (event.isFromGuild() && MODERATION.equals(commandGroup) && !member.hasPermission(Permission.ADMINISTRATOR)) {
                return;
            }

            if (SYSTEM.equals(commandGroup) && !user.equals(botOwner)) {
                return;
            }

            BotContext botContext = new BotContext();
            botContext.setBotOwner(botOwner);

            command.execute(event, botContext);
        }
    }
}
