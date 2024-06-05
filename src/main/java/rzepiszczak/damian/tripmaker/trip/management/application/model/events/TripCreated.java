package rzepiszczak.damian.tripmaker.trip.management.application.model.events;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

import java.time.LocalDate;

@RequiredArgsConstructor
public class TripCreated implements DomainEvent {
    private final String tripId;
    private final LocalDate from;
    private final LocalDate to;
}
