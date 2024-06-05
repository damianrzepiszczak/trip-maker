package rzepiszczak.damian.tripmaker.trip.management.application.model

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher
import rzepiszczak.damian.tripmaker.common.exception.DomainException
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.DayInformation
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.TripStarted
import rzepiszczak.damian.tripmaker.trip.management.infrastructure.persistence.TripPersistenceConfiguration
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.AssignPlanCommand
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.CreateNewTripCommand
import spock.lang.Specification

import java.time.LocalDate

class TripManagementTest extends Specification {

    private LocalDate someDay = LocalDate.of(2024, 5, 15)
    private TravelerId travelerId = TravelerId.from(UUID.randomUUID())
    private TripPersistenceConfiguration configuration = new TripPersistenceConfiguration()
    private Trips trips = configuration.tripRepository()
    private DomainEventPublisher domainEventPublisher = Mock()
    private TripService tripService = new TripManagement(trips, new MockClock(someDay), new TripFactory(trips), domainEventPublisher)

    def 'should create trip after choosing destination and period'() {
        when: 'for given destination and period create trip'
            tripService.create(new CreateNewTripCommand(travelerId, "Los Angeles", someDay, someDay.plusDays(5)))
        then: 'trip was created'
            List<Trip> travelerTrips = trips.findTravelerTrips(travelerId)
            travelerTrips.size() == 1
            1 * domainEventPublisher.publish(_)
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
            TripId tripId = tripService.create(new CreateNewTripCommand(travelerId, "Madrid", someDay, someDay.plusDays(5)))
        when: 'create new timeline based on plan'
            tripService.assignPlan(new AssignPlanCommand(tripId))
            tripService.start(tripId)
        then: 'trip can be started'
            2 * domainEventPublisher.publish(_)
    }

    def 'can finish trip if it is started'() {
        given:
            TripId tripId = tripService.create(new CreateNewTripCommand(travelerId, "London", someDay, someDay.plusDays(2)))
        when:
            tripService.assignPlan(new AssignPlanCommand(tripId))
            tripService.start(tripId)
            tripService.finish(tripId)
        then: 'traveler can finish trip because is started'
            3 * domainEventPublisher.publish(_)
    }

    def 'create new timeline based on plan details'() {
        given: 'traveler want to organize Dubai trip based on sample plan details'
            TravelerId travelerId = TravelerId.from(UUID.randomUUID())
        and:
            TripId tripId = tripService.create(new CreateNewTripCommand(travelerId, "Dubai", someDay, someDay.plusDays(1)))
        when: 'assign plan'
            AssignPlanCommand assignPlanCommand = new AssignPlanCommand(tripId)
            assignPlanCommand.addDetail(someDay, new DayInformation("First day activities in Dubai", List.of("Deira")))
            tripService.assignPlan(assignPlanCommand)
        then: 'timeline was created and trip can be started'
            tripService.start(tripId)
            Trip trip = trips.findTravelerTrips(travelerId)[0]
            trip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted]
    }
}
