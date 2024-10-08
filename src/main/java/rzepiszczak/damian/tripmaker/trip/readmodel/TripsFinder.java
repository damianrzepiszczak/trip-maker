package rzepiszczak.damian.tripmaker.trip.readmodel;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.Trip;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TripId;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.Trips;

@RequiredArgsConstructor
class TripsFinder implements TripsView {

    private final Trips trips;

    @Override
    public TravelerTrips findTravelerTrips(TravelerId travelerId) {
        var travelerTrips = new TravelerTrips();
        trips.findTravelerTrips(travelerId).forEach(trip -> {
            var travelerTrip = new TravelerTrips.Trip();
            travelerTrip.setTripId(trip.getId().id());
            travelerTrip.setDestination(trip.getDestination().getName());
            travelerTrip.setFrom(trip.getPeriod().from());
            travelerTrip.setTo(trip.getPeriod().to());
            travelerTrips.add(travelerTrip);
        });
        return travelerTrips;
    }

    @Override
    public TravelerTrips.Trip findByTripId(TripId tripId) {
        Trip trip = trips.findById(tripId).orElseThrow(() -> new DomainException("trip not found " + tripId));
        TravelerTrips.Trip travelerTrip = new TravelerTrips.Trip();
        travelerTrip.setTripId(trip.getId().id());
        travelerTrip.setDestination(trip.getDestination().getName());
        travelerTrip.setFrom(trip.getPeriod().from());
        travelerTrip.setTo(trip.getPeriod().to());
        return travelerTrip;
    }
}
