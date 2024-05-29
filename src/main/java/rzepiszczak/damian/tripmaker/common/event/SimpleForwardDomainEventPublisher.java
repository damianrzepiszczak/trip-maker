package rzepiszczak.damian.tripmaker.common.event;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SimpleForwardDomainEventPublisher implements DomainEventPublisher {

    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(List<DomainEvent> toPublish) {
        events.addAll(toPublish);
    }
}
