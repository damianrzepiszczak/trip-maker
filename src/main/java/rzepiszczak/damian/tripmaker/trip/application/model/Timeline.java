package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;

import java.time.LocalDate;
import java.util.*;

class Timeline {

    @Getter
    private final TimelineId timelineId;
    private final List<TripDay> tripDays;

    Timeline(List<TripDay> tripDays) {
        this.tripDays = tripDays;
        this.timelineId = new TimelineId(UUID.randomUUID().toString());
    }

    void modifyNote(LocalDate day, String note) {
        TripDay activity = getDayActivity(day);
        activity.note = note;
    }

    void addNewAttraction(LocalDate day, String attraction) {
        TripDay tripDay = getDayActivity(day);
        tripDay.attractions.add(attraction);
    }

    void scheduleForPeriod(Period period) {
        long amountOfDaysToSchedule = period.howManyDays();
        for (int dayNumber = 0; dayNumber < amountOfDaysToSchedule; dayNumber++) {
            tripDays.get(dayNumber).day = period.getFrom().plusDays(dayNumber);
        }
    }

    private TripDay getDayActivity(LocalDate day) {
        Optional<TripDay> found = tripDays.stream()
                .filter(tripDay -> tripDay.day.equals(day))
                .findFirst();
        return found.orElseThrow(() -> new DomainException("Cannot find " + day + " activity"));
    }

    @AllArgsConstructor
    static class TripDay {
        private LocalDate day;
        private String note;
        private final List<String> attractions;
    }

    @RequiredArgsConstructor
    private static class TimelineId {
        private final String id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeline timeline)) return false;
        return timelineId != null && Objects.equals(timelineId, timeline.timelineId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
