package rzepiszczak.damian.tripmaker.planning;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

@RequiredArgsConstructor
public class TripCanceled implements DomainEvent {
    private final TripId tripId;
}
