package ru.threehundredbytes.jeeves.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
public class JeevesConfiguration {
    @Bean
    public Color primaryColor(@Value("${jeeves.color.primary}") String encodedColor) {
        return Color.decode(encodedColor);
    }
}
