package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripPeriod {

    private LocalDate from;
    private LocalDate to;

    public static TripPeriod from(LocalDate from, LocalDate to) {
        if (to.isAfter(from)) {
            return new TripPeriod(from, to);
        }
        throw new IllegalStateException("Cannot create trip with from date which is after to date");
    }

    int howManyDays() {
        return Period.between(from, to).getDays();
    }
}
