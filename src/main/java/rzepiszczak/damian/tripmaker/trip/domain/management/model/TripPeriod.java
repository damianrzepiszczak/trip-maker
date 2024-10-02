package rzepiszczak.damian.tripmaker.trip.domain.management.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record TripPeriod(LocalDate from, LocalDate to) {

    public static TripPeriod from(LocalDate from, LocalDate to) {
        Objects.requireNonNull(from, "trip from date cannot be null");
        Objects.requireNonNull(to, "trip to date cannot be null");
        if (to.isAfter(from)) {
            return new TripPeriod(from, to);
        }
        throw new IllegalStateException("Cannot create trip with from date which is after to date");
    }

    int howManyDays() {
        return Period.between(from, to).getDays();
    }
}
