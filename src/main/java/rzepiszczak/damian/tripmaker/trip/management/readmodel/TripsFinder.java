package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.Trip;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.Trips;

import java.util.List;

@RequiredArgsConstructor
class TripsFinder implements TripsView {

    private final Trips trips;

    @Override
    public List<Trip> findTravelerTrips(TravelerId travelerId) {
        return trips.findTravelerTrips(travelerId);
    }
}
