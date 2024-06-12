package rzepiszczak.damian.tripmaker.trip.hints.infractructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.hints.application.domain.Assistants;

@Configuration
public class AssistantsPersistenceConfiguration {

    @Bean
    public Assistants assistantsRepository() {
        return new AssistantsInMemoryRepository();
    }
}
