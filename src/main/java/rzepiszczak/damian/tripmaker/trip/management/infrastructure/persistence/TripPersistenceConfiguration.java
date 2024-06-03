package rzepiszczak.damian.tripmaker.trip.management.infrastructure.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.management.application.model.Trips;

@Configuration
public class TripPersistenceConfiguration {

    @Bean
    public Trips tripRepository() {
        return new InMemoryTripRepository();
    }
}
