package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.events.TripCreated;

import java.util.UUID;

@RequiredArgsConstructor
class AssistanceFacade implements AssistanceService {

    private final HintsGenerator hintsGenerator;
    private final Assistants assistants;

    @Override
    public void generateInitialHint(TripCreated tripCreated) {
        Assistant assistant = new Assistant(AssistanceId.from(UUID.randomUUID()), TripId.from(tripCreated.getTripId()));
        assistant.publishHint(hintsGenerator.generateInitialHint(tripCreated.getDestination()));
        assistants.save(assistant);
    }
}
