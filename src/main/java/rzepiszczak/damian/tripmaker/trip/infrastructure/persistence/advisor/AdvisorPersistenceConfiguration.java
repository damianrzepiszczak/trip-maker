package rzepiszczak.damian.tripmaker.trip.infrastructure.persistence.advisor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.Advisors;

@Configuration
public class AdvisorPersistenceConfiguration {

    @Bean
    public Advisors advisors() {
        return new AdvisorsLocalStore();
    }
}
