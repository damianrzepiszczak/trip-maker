package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.management.domain.model.Trips;

@Configuration
class ReadModelConfiguration {

    @Bean
    TripsView tripsView(Trips trips) {
        return new TripsFinder(trips);
    }
}
