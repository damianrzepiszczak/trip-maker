package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Period {

    private LocalDate from;
    private LocalDate to;

    public static Period from(LocalDate from, LocalDate to) {
        if (to.isAfter(from)) {
            return new Period(from, to);
        }
        throw new IllegalStateException("Cannot create trip with from date which is after to date");
    }

    long howManyDays() {
        return java.time.Period.between(from, to).getDays();
    }
}
