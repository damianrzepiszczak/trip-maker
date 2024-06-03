package rzepiszczak.damian.tripmaker.trip.management.application.model;

import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.CreateNewTripCommand;

public interface TripService {
    TripId create(CreateNewTripCommand command);
    void assignPlan(AssignPlanCommand command);
    void start(TripId tripId);
    void finish(TripId tripId);
}
