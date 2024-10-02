package rzepiszczak.damian.tripmaker.trip.readmodel;

import rzepiszczak.damian.tripmaker.trip.domain.management.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TripId;

public interface TripsView {
    TravelerTrips findTravelerTrips(TravelerId travelerId);
    TravelerTrips.Trip findByTripId(TripId tripId);
}
