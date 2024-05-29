package rzepiszczak.damian.tripmaker.common.event;

import java.util.List;

public interface DomainEventPublisher {
    void publish(List<DomainEvent> event);
}
