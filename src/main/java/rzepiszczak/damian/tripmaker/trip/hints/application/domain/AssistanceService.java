package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import rzepiszczak.damian.tripmaker.trip.management.application.domain.events.TripCreated;

public interface AssistanceService {
    void generateInitialHint(TripCreated tripCreated);
}
