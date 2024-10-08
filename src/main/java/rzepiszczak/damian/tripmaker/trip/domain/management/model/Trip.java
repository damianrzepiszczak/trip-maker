package rzepiszczak.damian.tripmaker.trip.domain.management.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rzepiszczak.damian.tripmaker.common.AggregateRoot;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;
import rzepiszczak.damian.tripmaker.trip.application.management.commands.DayInformation;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.events.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Trip extends AggregateRoot<TripId> {

    @Getter
    private TravelerId travelerId;
    @Getter
    private Destination destination;
    @Getter
    private TripPeriod period;
    @Getter
    private TripStatus status;
    private List<TripDay> timeline;
    private List<Hint> hints;

    public Trip(TripId tripId, TravelerId travelerId, Destination destination, TripPeriod tripPeriod) {
        this.id = tripId;
        this.status = TripStatus.INCOMING;
        this.hints = new ArrayList<>();
        setDestination(destination);
        setTravelerId(travelerId);
        setTripPeriod(tripPeriod);
        registerEvent(new TripCreated(id.id(), tripPeriod.from(), tripPeriod.to()));
    }

    public void start(LocalDate startedAt) {
        if (!canStart(startedAt)) {
            throw new DomainException("Cannot start trip, assign plan or check possible start date");
        }
        status = TripStatus.STARTED;
        registerEvent(new TripStarted(id));
    }

    private boolean canStart(LocalDate now) {
        return (now.isBefore(period.from()) || now.isEqual(period.from()))
                && Period.between(now, period.from()).getDays() <= 1
                && isTimelineGenerated();
    }

    public void reschedule(TripPeriod newTripPeriod) {
        if (status != TripStatus.INCOMING || newTripPeriod.howManyDays() != period.howManyDays()) {
            throw new DomainException("New Period has different amount of days");
        }
        this.period = newTripPeriod;
        if (isTimelineGenerated()) {
            scheduleForPeriod(newTripPeriod);
        }
        registerEvent(new TripTimelineRescheduled(id));
    }

    private void scheduleForPeriod(TripPeriod tripPeriod) {
        int amountOfDaysToSchedule = tripPeriod.howManyDays();
        IntStream.range(0, amountOfDaysToSchedule).forEach(dayNumber -> {
            TripDay tripDay = timeline.get(dayNumber);
            tripDay.changeDayDate(tripPeriod.from().plusDays(dayNumber));
        });
    }

    public void finish() {
        if (status != TripStatus.STARTED) {
            throw new DomainException("Cannot finish not started trip");
        }
        status = TripStatus.FINISHED;
        registerEvent(new TripFinished(id));
    }

    public void cancel() {
        if (status == TripStatus.STARTED) {
            throw new DomainException("Cannot cancel started trip");
        }
        status = TripStatus.CANCELLED;
        registerEvent(new TripCanceled(id));
    }

    public void generateTimeline(Map<LocalDate, DayInformation> details) {
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

    public void publishHint(Hint hint) {
        hints.add(hint);
    }

    public List<Hint> hints() {
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

    private void setTripPeriod(TripPeriod tripPeriod) {
        Objects.requireNonNull(tripPeriod, "trip period is required");
        this.period = tripPeriod;
    }

    private void setTravelerId(TravelerId travelerId) {
        Objects.requireNonNull(travelerId, "traveler id is required");
        this.travelerId = travelerId;
    }

    private void setDestination(Destination destination) {
        Objects.requireNonNull(destination, "trip destination is required");
        this.destination = destination;
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
