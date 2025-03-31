package rzepiszczak.damian.tripmaker.trip.domain.advisor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.application.advisor.AdvisorFacade;
import rzepiszczak.damian.tripmaker.trip.application.advisor.TripEventsHandler;

@Configuration
public class AdvisorConfiguration {

    @Bean
    public AdvisorService advisorService(Advisors advisors) {
        return new AdvisorFacade(advisors);
    }

    @Bean
    public TripEventsHandler tripEventsHandler(AdvisorService advisorService) {
        return new TripEventsHandler(advisorService);
    }
}
