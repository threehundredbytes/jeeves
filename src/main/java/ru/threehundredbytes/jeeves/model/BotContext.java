package ru.threehundredbytes.jeeves.model;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

@Getter
@Setter
public class BotContext {
    private User botOwner;
}
