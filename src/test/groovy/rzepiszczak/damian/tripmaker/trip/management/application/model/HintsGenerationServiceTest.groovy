package rzepiszczak.damian.tripmaker.trip.management.application.model

import rzepiszczak.damian.tripmaker.common.Clock
import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.common.event.DomainEventPublisher
import rzepiszczak.damian.tripmaker.trip.management.application.commands.AssignPlanCommand
import rzepiszczak.damian.tripmaker.trip.management.application.commands.CreateNewTripCommand
import rzepiszczak.damian.tripmaker.trip.management.domain.model.HintsGenerationService
import rzepiszczak.damian.tripmaker.trip.management.domain.model.HintsGenerator
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TravelerId
import rzepiszczak.damian.tripmaker.trip.management.domain.model.Trip
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripFactory
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripId
import rzepiszczak.damian.tripmaker.trip.management.domain.TripManagement
import rzepiszczak.damian.tripmaker.trip.management.domain.TripService
import rzepiszczak.damian.tripmaker.trip.management.domain.model.Trips
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripsSettings
import rzepiszczak.damian.tripmaker.trip.management.infrastructure.persistence.TripPersistenceConfiguration
import spock.lang.Specification

import java.time.LocalDate

class HintsGenerationServiceTest extends Specification {

    private TravelerId travelerId = TravelerId.from(UUID.randomUUID())
    private LocalDate someDay = LocalDate.of(2024, 5, 15)
    private Clock clock = new MockClock(someDay)
    private Trips trips = new TripPersistenceConfiguration().tripRepository()
    private TripFactory tripFactory = new TripFactory(trips, new MockTripsSettings())
    private HintsGenerator hintsGenerator = new HintsGenerator(clock)
    private TripService tripService = new TripManagement(trips, clock, tripFactory, Mock(DomainEventPublisher), hintsGenerator)
    private HintsGenerationService hintsGenerationService = new HintsGenerationService(hintsGenerator, trips)

    def 'should generate new daily hint'() {
        given: 'trip is created'
        TripId tripId = tripService.create(new CreateNewTripCommand(travelerId, "Paris", someDay, someDay.plusDays(5)))
        and: 'assign plan'
            tripService.assignPlan(new AssignPlanCommand(tripId))
        and: 'start trip'
            tripService.start(tripId)
        when: 'new day begins'
            hintsGenerationService.generate(someDay.plusDays(1))
        then: 'daily hint should be generated'
        Trip trip = trips.findById(tripId).get()
            trip.hints().size() == 2
            trip.hints()[1].content == "Daily Paris hint"
    }

    class MockTripsSettings implements TripsSettings {
        @Override
        int allowedNumberOfTrips() {
            return 3
        }
    }
}
