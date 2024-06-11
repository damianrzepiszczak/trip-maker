package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import rzepiszczak.damian.tripmaker.common.AggregateRoot;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assistance extends AggregateRoot<AssistanceId> {

    private final TripId tripId;

    Assistance(AssistanceId assistanceId, TripId tripId) {
        this.id = assistanceId;
        this.tripId = tripId;
    }

    private final List<Hint> hints = new ArrayList<>();

    void publishHint(Hint hint) {
        hints.add(hint);
    }

    List<Hint> hints() {
        return Collections.unmodifiableList(hints);
    }
}
