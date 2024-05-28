package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
class TripFacade implements TripService {

    private final Trips trips;
    private final Clock clock;
    private final TripFactory tripFactory;

    @Override
    public void create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = tripFactory.create(travelerId, destination, from, to);
        trips.save(trip);
    }

    @Override
    public void assignPlan(AssignPlanCommand command) {
        Optional<Trip> found = trips.findById(command.getTripId());
        found.ifPresent(trip -> trip.assign(createTimeline(command)));
    }

    @Override
    public void start(TripId tripId) {
        Optional<Trip> found = trips.findById(tripId);
        found.ifPresent(trip -> trip.start(clock.now()));
    }

    @Override
    public void finish(TripId tripId) {
        trips.findById(tripId)
                .ifPresent(Trip::finish);
    }

    @Override
    public void share(TripId tripId) {
        trips.findById(tripId)
                .ifPresent(Trip::share);
    }

    private Timeline createTimeline(AssignPlanCommand request) {
        Timeline timeline = new Timeline(request.getPlanId());
        request.getDetails().forEach((day, information) -> timeline.assignDayActivity(new DayActivity(day, information.note(), information.attractions())));
        return timeline;
    }
}
