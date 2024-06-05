package rzepiszczak.damian.tripmaker.trip.hints.infrastructure;

import rzepiszczak.damian.tripmaker.trip.hints.application.model.Assistant;
import rzepiszczak.damian.tripmaker.trip.hints.application.model.AssistantId;
import rzepiszczak.damian.tripmaker.trip.hints.application.model.Assistants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class InMemoryTripAssistants implements Assistants {

    private final List<Assistant> assistants = new ArrayList<>();

    @Override
    public Optional<Assistant> findTripAssistant(AssistantId assistantId) {
        return assistants.stream()
                .filter(assistant -> assistant.getId().equals(assistantId))
                .findFirst();
    }

    @Override
    public void create(Assistant assistant) {
        assistants.add(assistant);
    }
}
