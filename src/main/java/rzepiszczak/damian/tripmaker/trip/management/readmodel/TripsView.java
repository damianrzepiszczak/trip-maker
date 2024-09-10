package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import rzepiszczak.damian.tripmaker.trip.management.domain.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripId;

public interface TripsView {
    TravelerTrips findTravelerTrips(TravelerId travelerId);
    TravelerTrips.Trip findByTripId(TripId tripId);
}
