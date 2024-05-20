package rzepiszczak.damian.tripmaker.planning;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

@RequiredArgsConstructor
public class TripShared implements DomainEvent {
    private final TripId tripId;
}
