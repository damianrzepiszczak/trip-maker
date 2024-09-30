package rzepiszczak.damian.tripmaker.trip.management.domain.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.common.MockClock;
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher;
import rzepiszczak.damian.tripmaker.trip.management.application.TripManagement;
import rzepiszczak.damian.tripmaker.trip.management.domain.TripService;

import java.time.LocalDate;

@Configuration
class TripConfiguration {

    @Bean
    TripService tripService(Trips trips, DomainEventPublisher domainEventPublisher, TripsSettings tripsSettings) {
        Clock clock = new MockClock(LocalDate.now());
        HintsGenerator hintsGenerator = new HintsGenerator(clock);
        return new TripManagement(trips, clock, new TripFactory(trips, tripsSettings), domainEventPublisher, hintsGenerator);
    }
}
