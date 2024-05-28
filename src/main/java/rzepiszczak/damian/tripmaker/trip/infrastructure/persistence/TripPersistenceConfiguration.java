package rzepiszczak.damian.tripmaker.trip.infrastructure.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.application.model.Trips;

@Configuration
public class TripPersistenceConfiguration {

    @Bean
    public Trips tripRepository() {
        return new InMemoryTripRepository();
    }
}
