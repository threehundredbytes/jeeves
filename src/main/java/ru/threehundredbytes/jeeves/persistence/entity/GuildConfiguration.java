package ru.threehundredbytes.jeeves.persistence.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GuildConfiguration {
    @Id
    private Long guildId;

    private String prefix;
}
