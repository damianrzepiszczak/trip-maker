package rzepiszczak.damian.tripmaker.trip.management.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.CreateNewTripCommand;
import rzepiszczak.damian.tripmaker.trip.management.domain.model.*;

@RequiredArgsConstructor
public class TripManagement implements TripService {

    private final Trips trips;
    private final Clock clock;
    private final TripFactory tripFactory;
    private final DomainEventPublisher domainEventPublisher;
    private final HintsGenerator hintsGenerator;

    @Override
    @Transactional
    public TripId create(CreateNewTripCommand createNewTripCommand) {
        var trip = tripFactory.create(createNewTripCommand.travelerId(),
                createNewTripCommand.destination(), createNewTripCommand.from(), createNewTripCommand.to());
        trip.publishHint(hintsGenerator.generateInitialHint(trip));
        trips.save(trip);
        domainEventPublisher.publish(trip.domainEvents());
        return trip.getId();
    }

    @Override
    @Transactional
    public void assignPlan(AssignPlanCommand command) {
        Trip trip = findTrip(command.getTripId());
        trip.generateTimeline(command.getDetails());
        domainEventPublisher.publish(trip.domainEvents());
    }

    @Override
    @Transactional
    public void start(TripId tripId) {
        var trip = findTrip(tripId);
        trip.start(clock.now());
        domainEventPublisher.publish(trip.domainEvents());
    }

    @Override
    @Transactional
    public void finish(TripId tripId) {
        var trip = findTrip(tripId);
        trip.finish();
        trip.publishHint(hintsGenerator.generateHintAfterTripFinishing(trip));
        domainEventPublisher.publish(trip.domainEvents());
    }

    private Trip findTrip(TripId tripId) {
        return trips.findById(tripId)
                .orElseThrow(() -> new DomainException("Cannot find requested trip " + tripId));
    }
}
