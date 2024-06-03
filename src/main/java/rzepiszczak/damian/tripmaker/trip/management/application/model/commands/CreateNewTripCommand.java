package rzepiszczak.damian.tripmaker.trip.management.application.model.commands;

import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;

import java.time.LocalDate;

public record CreateNewTripCommand(TravelerId travelerId, String destination, LocalDate from, LocalDate to) {
}
