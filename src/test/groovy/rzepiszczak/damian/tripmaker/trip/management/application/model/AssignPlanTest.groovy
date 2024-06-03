package rzepiszczak.damian.tripmaker.trip.management.application.model

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.common.event.SimpleForwardDomainEventPublisher
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.CreateNewTripCommand
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.management.infrastructure.persistence.TripPersistenceConfiguration
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.AssignPlanCommand
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.DayInformation
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.management.application.model.events.TripStarted
import spock.lang.Specification

import java.time.LocalDate

class AssignPlanTest extends Specification {

    private LocalDate someDay = LocalDate.of(2024, 5, 15)
    private TripPersistenceConfiguration configuration = new TripPersistenceConfiguration()
    private Trips trips = configuration.tripRepository()
    private TripService tripService = new TripManagement(trips, new MockClock(someDay), new TripFactory(trips), new SimpleForwardDomainEventPublisher())

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
