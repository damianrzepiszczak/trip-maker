package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.Trip;
import rzepiszczak.damian.tripmaker.trip.management.application.model.Trips;

import java.util.List;

@RequiredArgsConstructor
class TripsFinder implements TripsView {

    private final Trips trips;

    @Override
    public List<Trip> findTravelerTrips(TravelerId travelerId) {
        return trips.findTravelerTrips(travelerId);
    }
}
