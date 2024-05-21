package rzepiszczak.damian.tripmaker.trip.model.events;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.model.TripId;

@RequiredArgsConstructor
public class TripShared implements DomainEvent {
    private final TripId tripId;
}
