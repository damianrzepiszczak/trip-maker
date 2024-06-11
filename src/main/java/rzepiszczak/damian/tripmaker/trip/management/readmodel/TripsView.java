package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import rzepiszczak.damian.tripmaker.trip.management.application.domain.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.Trip;

import java.util.List;

public interface TripsView {
    List<Trip> findTravelerTrips(TravelerId travelerId);
}
