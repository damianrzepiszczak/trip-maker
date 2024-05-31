package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Period {

    private LocalDateTime from;
    private LocalDateTime to;

    public static Period from(LocalDateTime from, LocalDateTime to) {
        if (to.isAfter(from)) {
            return new Period(from, to);
        }
        throw new IllegalStateException("Cannot create trip with from date which is after to date");
    }

    long howManyDays() {
        return Duration.between(from, to).toDays();
    }
}
