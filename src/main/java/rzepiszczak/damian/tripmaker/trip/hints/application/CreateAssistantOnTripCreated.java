package rzepiszczak.damian.tripmaker.trip.hints.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import rzepiszczak.damian.tripmaker.trip.hints.application.model.AssistantService;
import rzepiszczak.damian.tripmaker.trip.hints.application.model.CreateNewAssistantCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.TripCreated;

@RequiredArgsConstructor
class CreateAssistantOnTripCreated {

    private final AssistantService assistantService;

    @EventListener
    void handle(TripCreated tripCreated) {
        CreateNewAssistantCommand createNewAssistantCommand = new CreateNewAssistantCommand(tripCreated.getTripId(), tripCreated.getFrom(), tripCreated.getTo());
        assistantService.createAssistanceForNewTrip(createNewAssistantCommand);
    }
}
