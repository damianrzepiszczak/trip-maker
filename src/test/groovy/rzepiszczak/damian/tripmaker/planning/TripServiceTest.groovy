package rzepiszczak.damian.tripmaker.planning

import spock.lang.Specification

import java.time.LocalDateTime

class TripServiceTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private TripRepository tripRepository = new TripRepository()
    private TripService tripService = new TripService(tripRepository)

    def 'should start trip after timeline assignment'() {
        given:
            PlanDetails planDetails = new PlanDetails()
        and:
            Trip trip = TripFactory.create("Madrid", someDay, someDay.plusDays(7))
            tripRepository.save(trip)
        when:
            tripService.assignPlan(trip.tripId, planDetails)
        then:
            Optional<Trip> found = tripRepository.findById(trip.tripId)
            found.get().start(someDay).isSuccessful()
    }
}
