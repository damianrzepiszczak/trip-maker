package rzepiszczak.damian.tripmaker.trip.management.domain;

import rzepiszczak.damian.tripmaker.trip.management.application.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.CreateNewTripCommand;
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripId;

public interface TripService {
    TripId create(CreateNewTripCommand command);
    void assignPlan(AssignPlanCommand command);
    void start(TripId tripId);
    void finish(TripId tripId);
}
