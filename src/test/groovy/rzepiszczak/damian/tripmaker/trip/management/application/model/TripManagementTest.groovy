package rzepiszczak.damian.tripmaker.trip.management.application.model

import rzepiszczak.damian.tripmaker.common.Clock
import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher
import rzepiszczak.damian.tripmaker.common.exception.DomainException
import rzepiszczak.damian.tripmaker.trip.management.application.commands.AssignPlanCommand
import rzepiszczak.damian.tripmaker.trip.management.application.commands.CreateNewTripCommand
import rzepiszczak.damian.tripmaker.trip.management.application.commands.DayInformation
import rzepiszczak.damian.tripmaker.trip.management.domain.model.Hint
import rzepiszczak.damian.tripmaker.trip.management.domain.model.HintsGenerator
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TravelerId
import rzepiszczak.damian.tripmaker.trip.management.domain.model.Trip
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripFactory
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripId
import rzepiszczak.damian.tripmaker.trip.management.domain.TripManagement
import rzepiszczak.damian.tripmaker.trip.management.domain.TripService
import rzepiszczak.damian.tripmaker.trip.management.domain.model.Trips
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripsSettings
import rzepiszczak.damian.tripmaker.trip.management.domain.model.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.management.domain.model.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.management.domain.model.events.TripStarted
import rzepiszczak.damian.tripmaker.trip.management.infrastructure.persistence.TripPersistenceConfiguration
import spock.lang.Specification

import java.time.LocalDate

class TripManagementTest extends Specification {

    private LocalDate someDay = LocalDate.of(2024, 5, 15)
    private Clock clock = new MockClock(someDay)
    private TravelerId travelerId = TravelerId.from(UUID.randomUUID())
    private TripPersistenceConfiguration configuration = new TripPersistenceConfiguration()
    private Trips trips = configuration.tripRepository()
    private DomainEventPublisher domainEventPublisher = Mock()
    private TripService tripService = new TripManagement(trips, clock, new TripFactory(trips, new MockTripsSettings()), domainEventPublisher, new HintsGenerator(clock))

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

    def 'cannot create trip if max allowed number is exceeded'() {
        given: 'traveler creates number of trips'
            tripService.create(new CreateNewTripCommand(travelerId, "Dubai", someDay, someDay.plusDays(2)))
            tripService.create(new CreateNewTripCommand(travelerId, "Paris", someDay, someDay.plusDays(5)))
            tripService.create(new CreateNewTripCommand(travelerId, "Madrid", someDay, someDay.plusDays(7)))
        when: 'traveler is creating next trip and he exceeds number of allowed'
            tripService.create(new CreateNewTripCommand(travelerId, "Thailand", someDay, someDay.plusDays(12)))
        then: 'cannot create next trip'
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

    def 'generate initial hint whenever new trip created'() {
        given:
            TravelerId travelerId = TravelerId.from(UUID.randomUUID())
         when: 'traveler creates new trip'
            TripId tripId = tripService.create(new CreateNewTripCommand(travelerId, "Paris", someDay, someDay.plusDays(5)))
         then: 'initial hints are generated'
            Trip trip = trips.findById(tripId).get()
            List<Hint> hints = trip.hints()
            hints.size() == 1
          and:
            hints[0].getContent() == "New trip to Paris was created. We will push new hints soon to improve your dreams"
    }

    def 'generate hint after finishing trip'() {
        given:
            TravelerId travelerId = TravelerId.from(UUID.randomUUID())
        when: 'traveler finishes trip'
            TripId tripId = tripService.create(new CreateNewTripCommand(travelerId, "Paris", someDay, someDay.plusDays(5)))
            Trip trip = trips.findById(tripId).get()
            AssignPlanCommand assignPlanCommand = new AssignPlanCommand(tripId)
            tripService.assignPlan(assignPlanCommand)
            tripService.start(tripId)
            tripService.finish(tripId)
        then: 'finishing hint is generated'
            List<Hint> hints = trip.hints()
            hints.size() == 2
        and:
            hints[1].getContent() == "Your Paris trip is finished. I hope we help yours dream come true"
    }

    class MockTripsSettings implements TripsSettings {

        @Override
        int allowedNumberOfTrips() {
            return 3
        }
    }
}
