package rzepiszczak.damian.tripmaker.trip.management.application.model.events;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;

@RequiredArgsConstructor
public class TimelineCreated implements DomainEvent {
    private final TripId tripId;
}
