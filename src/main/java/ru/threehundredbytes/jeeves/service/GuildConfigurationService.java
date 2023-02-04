package ru.threehundredbytes.jeeves.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.threehundredbytes.jeeves.persistence.entity.GuildConfiguration;
import ru.threehundredbytes.jeeves.persistence.repository.GuildConfigurationRepository;

@Service
@RequiredArgsConstructor
public class GuildConfigurationService {
    private final GuildConfigurationRepository guildConfigurationRepository;

    @Value("${jeeves.prefix.default}")
    private String defaultPrefix;

    public GuildConfiguration getGuildConfiguration(Guild guild) {
        return guildConfigurationRepository.findById(guild.getIdLong())
                .orElseGet(() -> createGuildConfiguration(guild));
    }

    public void setPrefix(String prefix, Guild guild) {
        var guildConfiguration = guildConfigurationRepository.findById(guild.getIdLong())
                .orElseGet(() -> createGuildConfiguration(guild));

        guildConfiguration.setPrefix(prefix);

        guildConfigurationRepository.save(guildConfiguration);
    }

    private GuildConfiguration createGuildConfiguration(Guild guild) {
        var guildConfiguration = GuildConfiguration.builder()
                .guildId(guild.getIdLong())
                .prefix(defaultPrefix)
                .build();

        return guildConfigurationRepository.save(guildConfiguration);
    }
}
