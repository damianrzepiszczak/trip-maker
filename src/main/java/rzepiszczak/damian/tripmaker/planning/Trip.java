package rzepiszczak.damian.tripmaker.planning;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.common.Result;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static rzepiszczak.damian.tripmaker.common.Result.failure;
import static rzepiszczak.damian.tripmaker.common.Result.success;
import static rzepiszczak.damian.tripmaker.planning.Trip.Stage.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Trip {

    enum Stage {PLANNING, STARTED, FINISHED, CANCELLED}

    private String destination;
    private LocalDateTime from;
    private LocalDateTime to;
    private Stage stage = PLANNING;
    private Plan activePlan;
    private CancelReason cancelReason;

    private final List<DomainEvent> events = new ArrayList<>();

    Trip(String destination, LocalDateTime from, LocalDateTime to) {
        this.destination = destination;
        this.from = from;
        this.to = to;
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
        return (now.isBefore(from) || now.isEqual(from))
                && Duration.between(now, from).toDays() <= 1
                && activePlan != null;
    }

    Result finish() {
        if (stage == STARTED) {
            stage = FINISHED;
            return success();
        }
        return failure();
    }

    Result cancel(String reason) {
        if (stage == PLANNING) {
            stage = CANCELLED;
            cancelReason = new CancelReason(reason);
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

    void assign(Plan plan) {
        this.activePlan = plan;
    }

    public List<DomainEvent> events() {
        return Collections.unmodifiableList(events);
    }
}
