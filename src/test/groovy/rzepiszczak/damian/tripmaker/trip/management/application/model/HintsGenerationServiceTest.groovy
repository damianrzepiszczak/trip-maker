package rzepiszczak.damian.tripmaker.trip.management.application.model

import rzepiszczak.damian.tripmaker.common.Clock
import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.trip.management.infrastructure.persistence.TripPersistenceConfiguration
import spock.lang.Specification

import java.time.LocalDate

class HintsGenerationServiceTest extends Specification {

    private TravelerId travelerId = TravelerId.from(UUID.randomUUID())
    private LocalDate someDay = LocalDate.of(2024, 5, 15)
    private Clock clock = new MockClock(someDay)
    private Trips trips = new TripPersistenceConfiguration().tripRepository()
    private TripFactory tripFactory = new TripFactory(trips, new MockTripsSettings())
    private HintsGenerationService hintsGenerationService = new HintsGenerationService(new HintsGenerator(clock), trips)

    def 'should generate new daily hint'() {
        given: 'Trip last couple of days'
            Trip trip = tripFactory.create(travelerId, "Paris", someDay, someDay.plusDays(5))
            trips.save(trip)
        when: 'New day begins'
            hintsGenerationService.generate(someDay.plusDays(1))
        then: 'Daily hint should be generated'
            trip.hints().size() == 1
            trip.hints()[0].content == "Day Paris hint"
    }

    class MockTripsSettings implements TripsSettings {
        @Override
        int allowedNumberOfTrips() {
            return 3
        }
    }
}
