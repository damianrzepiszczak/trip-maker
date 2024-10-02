package rzepiszczak.damian.tripmaker.trip.domain.management;

import rzepiszczak.damian.tripmaker.trip.application.management.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.application.management.commands.CreateNewTripCommand;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TripId;

public interface TripService {
    TripId create(CreateNewTripCommand command);
    void assignPlan(AssignPlanCommand command);
    void start(TripId tripId);
    void finish(TripId tripId);
}
