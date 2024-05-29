package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.*;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;
import rzepiszczak.damian.tripmaker.trip.application.model.events.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static rzepiszczak.damian.tripmaker.trip.application.model.Trip.Stage.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Trip {

    enum Stage {PLANNING, STARTED, FINISHED, CANCELLED}

    @Getter
    @Setter
    private TripId tripId;
    @Getter
    private TravelerId travelerId;
    @Getter
    private Destination destination;
    private Period period;
    private Stage stage = PLANNING;
    private Timeline timeline;
    private final List<DomainEvent> events = new ArrayList<>();

    Trip(TravelerId travelerId, Destination destination, Period period) {
        this.destination = destination;
        this.period = period;
        this.travelerId = travelerId;
        events.add(new TripCreated("TripId"));
    }

    void start(LocalDateTime startedAt) {
        if (!canStart(startedAt)) {
            throw new DomainException("Cannot start trip, assign plan or check possible start date");
        }
        stage = STARTED;
        events.add(new TripStarted(tripId));
    }

    private boolean canStart(LocalDateTime now) {
        return (now.isBefore(period.getFrom()) || now.isEqual(period.getFrom()))
                && Duration.between(now, period.getFrom()).toDays() <= 1
                && timeline != null;
    }

    void finish() {
        if (stage != STARTED) {
            throw new DomainException("Cannot finish not started trip");
        }
        stage = FINISHED;
        events.add(new TripFinished(tripId));
    }

    void cancel() {
        if (stage == STARTED) {
            throw new DomainException("Cannot cancel started trip");
        }
        stage = CANCELLED;
        events.add(new TripCanceled(tripId));
    }

    void share() {
        if (stage != FINISHED) {
            throw new DomainException("Cannot share not finished trip");
        }
        events.add(new TripShared(tripId));
    }

    void assign(Timeline timeline) {
        this.timeline = timeline;
        events.add(new TimelineCreated(tripId));
    }

    public List<DomainEvent> events() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip trip)) return false;
        return tripId != null && Objects.equals(tripId, trip.tripId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
