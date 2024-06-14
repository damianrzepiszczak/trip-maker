package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;

public interface TripsView {
    TravelerTrips findTravelerTrips(TravelerId travelerId);
}
