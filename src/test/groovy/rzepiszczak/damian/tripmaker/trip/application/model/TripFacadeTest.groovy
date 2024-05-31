package rzepiszczak.damian.tripmaker.trip.application.model

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher
import rzepiszczak.damian.tripmaker.common.event.SimpleForwardDomainEventPublisher
import rzepiszczak.damian.tripmaker.common.exception.DomainException
import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand
import rzepiszczak.damian.tripmaker.trip.application.model.commands.CreateNewTripCommand
import rzepiszczak.damian.tripmaker.trip.application.model.events.*
import rzepiszczak.damian.tripmaker.trip.infrastructure.persistence.TripPersistenceConfiguration
import spock.lang.Specification

import java.time.LocalDateTime

class TripFacadeTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private TravelerId travelerId = TravelerId.from(UUID.randomUUID())
    private TripPersistenceConfiguration configuration = new TripPersistenceConfiguration()
    private Trips trips = configuration.tripRepository()
    private DomainEventPublisher domainEventPublisher = new SimpleForwardDomainEventPublisher()
    private TripService tripService = new TripFacade(trips, new MockClock(someDay), new TripFactory(trips), domainEventPublisher)

    def 'should create trip after choosing destination and period'() {
        when: 'for given destination and period create trip'
            tripService.create(new CreateNewTripCommand(travelerId, "Los Angeles", someDay, someDay.plusDays(5)))
        then: 'trip was created'
            List<Trip> travelerTrips = trips.findTravelerTrips(travelerId)
            travelerTrips.size() == 1
            travelerTrips[0].domainEvents()*.class == [TripCreated]
    }

    def 'cannot create trip with the same destination'() {
        given: 'traveler creates trip to Dubai'
            tripService.create(new CreateNewTripCommand(travelerId, "Dubai", someDay, someDay.plusDays(10)))
        when: 'traveler creates second trip to Dubai'
            tripService.create(new CreateNewTripCommand(travelerId, "Dubai", someDay, someDay.plusDays(7)))
        then: 'cannot create with the same destination'
            DomainException exception = thrown()
            exception.message == "Trying to create trip with the same destination"
    }

    def 'can create two trips with different destination'() {
        given: 'traveler creates trip to Dubai'
            tripService.create(new CreateNewTripCommand(travelerId, "Dubai", someDay, someDay.plusDays(10)))
        when: 'traveler creates second trip to Madeira'
            tripService.create(new CreateNewTripCommand(travelerId, "Madeira", someDay, someDay.plusDays(7)))
        then: 'can create with the same destination'
            trips.findTravelerTrips(travelerId).size() == 2
    }

    def 'can start trip after plan assignment'() {
        given: 'traveler wants to create trip based on plan'
            tripService.create(new CreateNewTripCommand(travelerId, "Madrid", someDay, someDay.plusDays(5)))
        and:
            Trip madridTrip = trips.findTravelerTrips(travelerId)[0]
        when: 'create new timeline based on plan'
            tripService.assignPlan(new AssignPlanCommand(madridTrip.getId()))
        then: 'trip can be started'
            madridTrip.start(someDay)
            madridTrip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'can finish trip if it is started'() {
        given:
            tripService.create(new CreateNewTripCommand(travelerId, "London", someDay, someDay.plusDays(2)))
        and:
            Trip trip = trips.findTravelerTrips(travelerId)[0]
            tripService.assignPlan(new AssignPlanCommand(trip.getId()))
        and:
            tripService.start(trip.getId())
        when:
            tripService.finish(trip.getId())
        then: 'traveler can finish trip because is started'
            trip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished]
    }

    def 'can share trip if it is finished'() {
        given: 'traveler wants to complete trip and share'
            tripService.create(new CreateNewTripCommand(travelerId, "London", someDay, someDay.plusDays(2)))
        and:
            Trip trip = trips.findTravelerTrips(travelerId)[0]
            tripService.assignPlan(new AssignPlanCommand(trip.getId()))
        and:
            tripService.start(trip.getId())
        and:
            tripService.finish(trip.getId())
        when: 'traveler shares trip'
            tripService.share(trip.getId())
        then: 'trip details can be shared after finishing it'
            trip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished, TripShared]
    }
}
