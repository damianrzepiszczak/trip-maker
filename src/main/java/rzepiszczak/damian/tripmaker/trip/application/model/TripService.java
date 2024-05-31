package rzepiszczak.damian.tripmaker.trip.application.model;

import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.CreateNewTripCommand;

public interface TripService {
    TripId create(CreateNewTripCommand command);
    void assignPlan(AssignPlanCommand command);
    void start(TripId tripId);
    void finish(TripId tripId);
    void share(TripId tripId);
}
