package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.Trip;

import java.util.List;

public interface TripsView {
    List<Trip> findTravelerTrips(TravelerId travelerId);
}
