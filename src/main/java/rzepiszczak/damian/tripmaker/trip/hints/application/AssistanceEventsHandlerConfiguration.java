package rzepiszczak.damian.tripmaker.trip.hints.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.hints.application.model.AssistantService;

@Configuration
class AssistanceEventsHandlerConfiguration {

    @Bean
    CreateAssistantOnTripCreated createAssistantOnTripCreated(AssistantService assistantService) {
        return new CreateAssistantOnTripCreated(assistantService);
    }
}
