package rzepiszczak.damian.tripmaker.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventsConfig {

    @Bean
    DomainEventPublisher domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new SimpleForwardDomainEventPublisher(applicationEventPublisher);
    }
}
