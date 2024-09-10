package rzepiszczak.damian.tripmaker.trip.management.domain.model;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class HintsGenerationService {

    private final HintsGenerator hintsGenerator;
    private final Trips trips;

    @Transactional
    public void generate(LocalDate date) {
        List<Trip> found = trips.findAllStarted();
        found.forEach(trip -> {
            Hint hint = hintsGenerator.generateDailyHint(trip, date);
            trip.publishHint(hint);
            trips.save(trip);
        });
    }
}
