package rzepiszczak.damian.tripmaker.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@RequiredArgsConstructor
public class SimpleForwardDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(List<DomainEvent> toPublish) {
        System.out.println(toPublish);
        toPublish.forEach(applicationEventPublisher::publishEvent);
    }
}
