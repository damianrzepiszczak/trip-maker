package rzepiszczak.damian.tripmaker.trip.hints.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.hints.application.model.Assistants;

@Configuration
public class AssistanceRepositoryConfiguration {

    @Bean
    public Assistants assistantsRepository() {
        return new InMemoryTripAssistants();
    }
}
