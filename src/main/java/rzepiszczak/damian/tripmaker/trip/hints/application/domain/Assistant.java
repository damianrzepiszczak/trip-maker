package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import lombok.Getter;
import lombok.ToString;
import rzepiszczak.damian.tripmaker.common.AggregateRoot;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
public class Assistant extends AggregateRoot<AssistanceId> {

    @Getter
    private final TripId tripId;

    Assistant(AssistanceId assistanceId, TripId tripId) {
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
