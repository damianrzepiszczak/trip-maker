package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rzepiszczak.damian.tripmaker.common.AggregateRoot;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.DayInformation;
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Trip extends AggregateRoot<TripId> {

    @Getter
    private TravelerId travelerId;
    @Getter
    private Destination destination;
    private Period period;
    private TripStatus status;
    private List<TripDay> timeline;
    private List<Hint> hints;

    Trip(TripId tripId, TravelerId travelerId, Destination destination, Period period) {
        this.id = tripId;
        this.destination = destination;
        this.period = period;
        this.travelerId = travelerId;
        this.status = TripStatus.INCOMING;
        this.hints = new ArrayList<>();
        generateHint("New trip to " + destination.getDestination() + " was created. We will push new hints soon to improve your dreams");
        registerEvent(new TripCreated(id.getId(), period.getFrom(), period.getTo()));
    }

    void start(LocalDate startedAt) {
        if (!canStart(startedAt)) {
            throw new DomainException("Cannot start trip, assign plan or check possible start date");
        }
        status = TripStatus.STARTED;
        registerEvent(new TripStarted(id));
    }

    private boolean canStart(LocalDate now) {
        return (now.isBefore(period.getFrom()) || now.isEqual(period.getFrom()))
                && java.time.Period.between(now, period.getFrom()).getDays() <= 1
                && isTimelineGenerated();
    }

    void reschedule(Period newPeriod) {
        if (status != TripStatus.INCOMING || newPeriod.howManyDays() != period.howManyDays()) {
            throw new DomainException("New Period has different amount of days");
        }
        this.period = newPeriod;
        if (isTimelineGenerated()) {
            scheduleForPeriod(newPeriod);
        }
        registerEvent(new TripTimelineRescheduled(id));
    }

    private void scheduleForPeriod(Period period) {
        int amountOfDaysToSchedule = period.howManyDays();
        IntStream.range(0, amountOfDaysToSchedule).forEach(dayNumber -> {
            TripDay tripDay = timeline.get(dayNumber);
            tripDay.changeDayDate(period.getFrom().plusDays(dayNumber));
        });
    }

    void finish() {
        if (status != TripStatus.STARTED) {
            throw new DomainException("Cannot finish not started trip");
        }
        status = TripStatus.FINISHED;
        registerEvent(new TripFinished(id));
    }

    void cancel() {
        if (status == TripStatus.STARTED) {
            throw new DomainException("Cannot cancel started trip");
        }
        status = TripStatus.CANCELLED;
        registerEvent(new TripCanceled(id));
    }

    void generateTimeline(Map<LocalDate, DayInformation> details) {
        timeline = new ArrayList<>();
        details.forEach((day, information) -> timeline.add(new TripDay(day, information.getNote(), information.getAttractions())));
        registerEvent(new TimelineCreated(id));
    }

    void modifyDayNote(LocalDate day, String note) {
        TripDay tripDay = getTripDay(day);
        tripDay.modifyNote(note);
    }

    void addNewAttraction(LocalDate day, String attraction) {
        if (status != TripStatus.INCOMING) {
            throw new DomainException("Cannot add new attraction for started trip");
        }
        TripDay tripDay = getTripDay(day);
        tripDay.newDatAttraction(attraction);
    }

    void generateHint(String content) {
        hints.add(new Hint(content));
    }

    List<Hint> hints() {
        return Collections.unmodifiableList(hints);
    }

    private TripDay getTripDay(LocalDate day) {
        Optional<TripDay> found = timeline.stream().
                filter(tripDay -> tripDay.getDay().equals(day))
                .findFirst();
        return found.orElseThrow(() -> new DomainException("Cannot find " + day + " activity"));
    }

    private boolean isTimelineGenerated() {
        return timeline != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip trip)) return false;
        return id != null && Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
