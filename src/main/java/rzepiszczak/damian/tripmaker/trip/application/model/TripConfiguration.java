package rzepiszczak.damian.tripmaker.trip.application.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.common.MockClock;
import rzepiszczak.damian.tripmaker.common.event.SimpleForwardDomainEventPublisher;

import java.time.LocalDateTime;

@Configuration
class TripConfiguration {

    @Bean
    TripService tripService(Trips trips) {
        return new TripFacade(trips, new MockClock(LocalDateTime.now()), new TripFactory(trips), new SimpleForwardDomainEventPublisher());
    }
}
