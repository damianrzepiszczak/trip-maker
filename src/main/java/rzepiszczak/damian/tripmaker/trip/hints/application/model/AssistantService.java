package rzepiszczak.damian.tripmaker.trip.hints.application.model;

public interface AssistantService {
    AssistantId createAssistanceForNewTrip(CreateNewAssistantCommand createNewAssistantCommand);
}
