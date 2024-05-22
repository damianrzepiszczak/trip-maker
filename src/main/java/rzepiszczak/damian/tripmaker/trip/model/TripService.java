package rzepiszczak.damian.tripmaker.trip.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.traveler.TravelerId;
import rzepiszczak.damian.tripmaker.trip.application.AssignPlanCommand;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class TripService {

    private final Trips trips;
    private final Clock clock;
    private final TripFactory tripFactory;

    void create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = tripFactory.create(travelerId, destination, from, to);
        trips.save(trip);
    }

    void assignPlan(TripId tripId, AssignPlanCommand request) {
        Optional<Trip> found = trips.findById(tripId);
        found.ifPresent(trip -> trip.assign(createTimeline(request)));
    }

    void start(TripId tripId) {
        Optional<Trip> found = trips.findById(tripId);
        found.ifPresent(trip -> trip.start(clock.now()));
    }

    private Timeline createTimeline(AssignPlanCommand request) {
        Timeline timeline = new Timeline(request.getPlanId());
        request.getDetails().forEach((day, information) -> timeline.assignDayActivity(new DayActivity(day, information.note(), information.attractions())));
        return timeline;
    }
}
