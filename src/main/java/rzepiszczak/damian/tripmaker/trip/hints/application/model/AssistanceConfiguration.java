package rzepiszczak.damian.tripmaker.trip.hints.application.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AssistanceConfiguration {

    @Bean
    AssistantService assistantService(Assistants assistants) {
        return new AssistantFacade(assistants);
    }
}
