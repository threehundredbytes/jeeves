package ru.threehundredbytes.jeeves.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;
import java.util.Map;

@Configuration
public class MessageConfiguration {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public Map<String, Locale> supportedLocales() {
        return Map.of("en", Locale.ENGLISH, "ru", Locale.forLanguageTag("ru"));
    }
}
