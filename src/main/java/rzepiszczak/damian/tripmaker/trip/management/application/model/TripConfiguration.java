package rzepiszczak.damian.tripmaker.trip.management.application.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.common.MockClock;
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher;

import java.time.LocalDate;

@Configuration
class TripConfiguration {

    @Bean
    TripService tripService(Trips trips, DomainEventPublisher domainEventPublisher) {
        return new TripManagement(trips, new MockClock(LocalDate.now()), new TripFactory(trips), domainEventPublisher);
    }
}
