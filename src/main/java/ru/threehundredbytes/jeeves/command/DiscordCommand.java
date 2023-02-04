package ru.threehundredbytes.jeeves.command;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Inherited
public @interface DiscordCommand {
    String key();
    CommandGroup group();
    CommandSource[] source() default { CommandSource.DIRECT_MESSAGE, CommandSource.GUILD_MESSAGE };
}
