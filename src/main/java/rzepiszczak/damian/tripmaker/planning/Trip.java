package rzepiszczak.damian.tripmaker.planning;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.common.Result;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static rzepiszczak.damian.tripmaker.common.Result.failure;
import static rzepiszczak.damian.tripmaker.common.Result.success;
import static rzepiszczak.damian.tripmaker.planning.Trip.Stage.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Trip {

    enum Stage {PLANNING, STARTED, FINISHED, CANCELLED}

    @Getter @Setter
    private TripId tripId;
    private String destination;
    private Period period;
    private Stage stage = PLANNING;
    private Timeline timeline;
    private final List<DomainEvent> events = new ArrayList<>();

    Trip(String destination, LocalDateTime from, LocalDateTime to) {
        this.destination = destination;
        this.period = new Period(from, to);
        events.add(new NewTripCreated("PlanId"));
    }

    Result start(LocalDateTime startedAt) {
        if (canStart(startedAt)) {
            stage = STARTED;
            return success();
        }
        return failure();
    }

    private boolean canStart(LocalDateTime now) {
        return (now.isBefore(period.from()) || now.isEqual(period.from()))
                && Duration.between(now, period.from()).toDays() <= 1
                && timeline != null;
    }

    Result finish() {
        if (stage == STARTED) {
            stage = FINISHED;
            return success();
        }
        return failure();
    }

    Result cancel() {
        if (stage == PLANNING) {
            stage = CANCELLED;
            return success();
        }
        return failure();
    }

    Result share() {
        if (stage == FINISHED) {
            return success();
        }
        return failure();
    }

    void assign(Timeline timeline) {
        this.timeline = timeline;
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
