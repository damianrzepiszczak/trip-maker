package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.Trip;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.Trips;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.events.TripCreated;

import java.util.UUID;

@RequiredArgsConstructor
class AssistanceFacade implements AssistanceService {

    private final HintsGenerator hintsGenerator;
    private final Trips trips;
    private final Assistants assistants;

    @Override
    public void generateInitialHint(TripCreated tripCreated) {
        Trip trip = trips.findById(TripId.from(tripCreated.getTripId())).orElseThrow(() -> new DomainException("Trip don't exist"));
        Assistance assistance = new Assistance(AssistanceId.from(UUID.randomUUID()), trip.getId());
        assistance.publishHint(hintsGenerator.generateInitialHint(trip));
        assistants.save(assistance);
    }
}
