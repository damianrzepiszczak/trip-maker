package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;

public interface TripsView {
    TravelerTrips findTravelerTrips(TravelerId travelerId);
    TravelerTrips.Trip findByTripId(TripId tripId);
}
