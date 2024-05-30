package rzepiszczak.damian.tripmaker.common;

import lombok.Getter;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot<T> {

    @Getter
    protected T id;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected void registerEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    public List<DomainEvent> domainEvents() {
        return domainEvents;
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
