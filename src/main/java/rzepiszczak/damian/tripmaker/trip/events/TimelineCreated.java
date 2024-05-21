package rzepiszczak.damian.tripmaker.trip.events;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.TripId;

@RequiredArgsConstructor
public class TimelineCreated implements DomainEvent {
    private final TripId tripId;
}
