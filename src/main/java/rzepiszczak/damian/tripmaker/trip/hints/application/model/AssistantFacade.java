package rzepiszczak.damian.tripmaker.trip.hints.application.model;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
class AssistantFacade implements AssistantService {

    private final Assistants assistants;

    @Override
    public AssistantId createAssistanceForNewTrip(CreateNewAssistantCommand createNewAssistantCommand) {
        Assistant assistant = new Assistant(AssistantId.from(UUID.randomUUID()), createNewAssistantCommand.getTripStart(), createNewAssistantCommand.getTripEnd());
        assistants.create(assistant);
        return assistant.getId();
    }
}
