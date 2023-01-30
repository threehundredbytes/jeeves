package ru.threehundredbytes.jeeves.command.utility;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.threehundredbytes.jeeves.command.Command;
import ru.threehundredbytes.jeeves.command.CommandGroup;
import ru.threehundredbytes.jeeves.command.DiscordCommand;
import ru.threehundredbytes.jeeves.model.BotContext;

import java.awt.*;

@DiscordCommand(key = "ping", group = CommandGroup.UTILITY)
public class PingCommand extends Command {
    public static final String PING_PONG_EMOJI = "\uD83C\uDFD3";
    public static final String GREEN_CIRCLE_EMOJI = "\uD83D\uDFE2";
    public static final String RED_CIRCLE_EMOJI = "\uD83D\uDD34";

    @Override
    public void execute(MessageReceivedEvent event, BotContext botContext) {
        Message message = event.getMessage();
        User botOwner = botContext.getBotOwner();

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

            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setTitle("Ping information")
                    .setDescription(String.format("Delay is %d ms", ping))
                    .addField("Heartbeat", event.getJDA().getGatewayPing() + " ms", false)
                    .addField("Ping rating", pingRating, false)
                    .setFooter("Developed by " + botOwner.getAsTag(), botOwner.getAvatarUrl())
                    .setColor(new Color(29, 78, 216))
                    .build();

            event.getChannel().sendMessageEmbeds(messageEmbed).queue();
        });
    }
}
