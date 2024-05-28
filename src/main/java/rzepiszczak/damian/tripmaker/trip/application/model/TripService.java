package rzepiszczak.damian.tripmaker.trip.application.model;

import rzepiszczak.damian.tripmaker.traveler.TravelerId;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand;

import java.time.LocalDateTime;

public interface TripService {
    void create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to);
    void assignPlan(AssignPlanCommand command);
    void start(TripId tripId);
    void finish(TripId tripId);
    void share(TripId tripId);
}
