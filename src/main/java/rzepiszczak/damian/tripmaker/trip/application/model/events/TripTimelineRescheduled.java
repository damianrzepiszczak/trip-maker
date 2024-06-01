package rzepiszczak.damian.tripmaker.trip.application.model.events;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.application.model.TripId;

@RequiredArgsConstructor
public class TripTimelineRescheduled implements DomainEvent {
    private final TripId tripId;
}
