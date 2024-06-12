package rzepiszczak.damian.tripmaker.trip.hints.infractructure;

import rzepiszczak.damian.tripmaker.trip.hints.application.domain.Assistant;
import rzepiszczak.damian.tripmaker.trip.hints.application.domain.Assistants;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AssistantsInMemoryRepository implements Assistants {

    private final List<Assistant> assistants = new ArrayList<>();

    @Override
    public void save(Assistant assistant) {
        assistants.add(assistant);
    }

    @Override
    public Optional<Assistant> findTripAssistant(TripId tripId) {
        return assistants.stream()
                .filter(assistant -> assistant.getTripId().equals(tripId))
                .findFirst();
    }
}
