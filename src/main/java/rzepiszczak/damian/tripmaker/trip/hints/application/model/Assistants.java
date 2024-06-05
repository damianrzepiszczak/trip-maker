package rzepiszczak.damian.tripmaker.trip.hints.application.model;

import java.util.Optional;

public interface Assistants {
    Optional<Assistant> findTripAssistant(AssistantId assistantId);
    void create(Assistant assistant);
}
