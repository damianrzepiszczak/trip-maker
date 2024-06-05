package rzepiszczak.damian.tripmaker.trip.management.application.model;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.common.MockClock;
import rzepiszczak.damian.tripmaker.common.event.SimpleForwardDomainEventPublisher;

import java.time.LocalDate;

@Configuration
class TripConfiguration {

    @Bean
    TripService tripService(Trips trips, ApplicationEventPublisher applicationEventPublisher) {
        return new TripManagement(trips, new MockClock(LocalDate.now()), new TripFactory(trips), new SimpleForwardDomainEventPublisher(applicationEventPublisher));
    }
}
