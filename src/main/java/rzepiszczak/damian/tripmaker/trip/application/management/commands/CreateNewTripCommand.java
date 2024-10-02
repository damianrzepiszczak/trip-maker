package rzepiszczak.damian.tripmaker.trip.application.management.commands;

import rzepiszczak.damian.tripmaker.trip.domain.management.model.TravelerId;

import java.time.LocalDate;

public record CreateNewTripCommand(TravelerId travelerId, String destination, LocalDate from, LocalDate to) {
}
