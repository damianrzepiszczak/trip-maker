package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.Clock;

import java.time.LocalDate;

@RequiredArgsConstructor
class HintsGenerator {

    private static final String INITIAL_HINT_CONTENT = "New trip to %s was created. We will push new hints soon to improve your dreams";
    private static final String LAST_TRIP_DAY_HINT_CONTENT = "Your %s trip is finished. I hope we help yours dream come true";

    private final Clock clock;

    Hint generateInitialHint(Trip trip) {
        return new Hint(String.format(INITIAL_HINT_CONTENT, trip.getDestination().getName()), clock.now());
    }

    Hint generateHintAfterTripFinishing(Trip trip) {
        return new Hint(String.format(LAST_TRIP_DAY_HINT_CONTENT, trip.getDestination().getName()), clock.now());
    }

    Hint generateDailyHint(Trip trip, LocalDate date) {
        return new Hint("Daily " + trip.getDestination().getName() + " hint", date);
    }
}
