package rzepiszczak.damian.tripmaker.trip.hints.application;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AssistantService {

    private final Assistants assistants;

    AssistantId createAssistanceForNewTrip(CreateNewAssistantCommand createNewAssistantCommand) {
        Assistant assistant = new Assistant(AssistantId.from(UUID.randomUUID()), createNewAssistantCommand.getTripStart(), createNewAssistantCommand.getTripEnd());
        assistants.create(assistant);
        return assistant.getId();
    }
}
