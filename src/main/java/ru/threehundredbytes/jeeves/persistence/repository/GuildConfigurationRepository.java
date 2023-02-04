package ru.threehundredbytes.jeeves.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.threehundredbytes.jeeves.persistence.entity.GuildConfiguration;

public interface GuildConfigurationRepository extends JpaRepository<GuildConfiguration, Long> {
}
