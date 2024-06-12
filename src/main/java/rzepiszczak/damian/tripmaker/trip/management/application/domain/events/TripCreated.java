package rzepiszczak.damian.tripmaker.trip.management.application.domain.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

import java.time.LocalDate;

@Getter
@ToString
@RequiredArgsConstructor
public class TripCreated implements DomainEvent {
    private final String tripId;
    private final LocalDate from;
    private final LocalDate to;
    private final String destination;
}
