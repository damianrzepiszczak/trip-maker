package rzepiszczak.damian.tripmaker.trip.management.application.domain.events;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;

@RequiredArgsConstructor
public class TripTimelineRescheduled implements DomainEvent {
    private final TripId tripId;
}
