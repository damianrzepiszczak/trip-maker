package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.CreateNewTripCommand;

import java.util.Optional;

@RequiredArgsConstructor
class TripManagement implements TripService {

    private final Trips trips;
    private final Clock clock;
    private final TripFactory tripFactory;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public TripId create(CreateNewTripCommand createNewTripCommand) {
        Trip trip = tripFactory.create(createNewTripCommand.travelerId(),
                createNewTripCommand.destination(), createNewTripCommand.from(), createNewTripCommand.to());
        trips.save(trip);
        domainEventPublisher.publish(trip.domainEvents());
        return trip.getId();
    }

    @Override
    public void assignPlan(AssignPlanCommand command) {
        Optional<Trip> found = trips.findById(command.getTripId());
        found.ifPresent(trip -> {
            trip.assignTimeline(TimelineCreator.create(command));
            domainEventPublisher.publish(trip.domainEvents());
        });
    }

    @Override
    public void start(TripId tripId) {
        Optional<Trip> found = trips.findById(tripId);
        found.ifPresent(trip -> {
            trip.start(clock.now());
            domainEventPublisher.publish(trip.domainEvents());
        });
    }

    @Override
    public void finish(TripId tripId) {
        trips.findById(tripId).ifPresent(trip -> {
            trip.finish();
            domainEventPublisher.publish(trip.domainEvents());
        });
    }
}
