package ru.threehundredbytes.jeeves.command.utility;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

import java.util.Locale;

@DiscordCommand(key = "command.utility.ping.key", group = CommandGroup.UTILITY)
public class PingCommand extends Command {
    public static final String PING_PONG_EMOJI = "\uD83C\uDFD3";
    public static final String GREEN_CIRCLE_EMOJI = "\uD83D\uDFE2";
    public static final String RED_CIRCLE_EMOJI = "\uD83D\uDD34";

    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        Message message = event.getMessage();

        long start = System.nanoTime();

        message.addReaction(Emoji.fromUnicode(PING_PONG_EMOJI)).queue();

        event.getChannel().sendTyping().queue(v -> {
            long end = System.nanoTime();
            long delta = end - start;
            long ping = delta / 1_000_000;

            String pingRating = "";

            final int CIRCLE_COUNT = 10;

            if (ping > 1000) {
                pingRating = RED_CIRCLE_EMOJI.repeat(CIRCLE_COUNT);
            } else {
                int redCount = (int) (ping / 100);
                int greenCount = CIRCLE_COUNT - redCount;

                pingRating += GREEN_CIRCLE_EMOJI.repeat(greenCount);
                pingRating += RED_CIRCLE_EMOJI.repeat(redCount);
            }

            Locale locale = botContext.locale();

            String title = messageService.getMessage("command.utility.ping.title", locale);
            String description = messageService.getMessage("command.utility.ping.description", locale, ping);

            MessageEmbed messageEmbed = messageService.getMessageEmbedBuilder(title, description)
                    .addField(getHeartbeatField(event.getJDA().getGatewayPing(), locale))
                    .addField(getPingRatingField(pingRating, locale))
                    .build();

            event.getChannel().sendMessageEmbeds(messageEmbed).queue();
        });
    }

    private MessageEmbed.Field getHeartbeatField(long gatewayPing, Locale locale) {
        String title = messageService.getMessage("command.utility.ping.field.heartbeat.title", locale);
        String description = messageService.getMessage("command.utility.ping.field.heartbeat.description",
                locale, gatewayPing);

        return new MessageEmbed.Field(title, description, false);
    }

    private MessageEmbed.Field getPingRatingField(String pingRating, Locale locale) {
        String title = messageService.getMessage("command.utility.ping.field.rating.title", locale);

        return new MessageEmbed.Field(title, pingRating, false);
    }
}
