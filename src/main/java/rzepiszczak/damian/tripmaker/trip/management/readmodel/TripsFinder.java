package rzepiszczak.damian.tripmaker.trip.management.readmodel;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.Trips;

@RequiredArgsConstructor
class TripsFinder implements TripsView {

    private final Trips trips;

    @Override
    public TravelerTrips findTravelerTrips(TravelerId travelerId) {
        var travelerTrips = new TravelerTrips();
        trips.findTravelerTrips(travelerId).forEach(trip -> {
            var travelerTrip = new TravelerTrips.Trip();
            travelerTrip.setTripId(trip.getId().getId());
            travelerTrip.setDestination(trip.getDestination().getName());
            travelerTrip.setFrom(trip.getPeriod().getFrom());
            travelerTrip.setTo(trip.getPeriod().getTo());
            travelerTrips.add(travelerTrip);
        });
        return travelerTrips;
    }
}
