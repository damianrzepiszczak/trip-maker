package rzepiszczak.damian.tripmaker.planning

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.traveler.TravelerId
import spock.lang.Specification

import java.time.LocalDateTime

class TripServiceTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private TripRepository tripRepository = new TripRepository()
    private TripService tripService = new TripService(tripRepository, new MockClock(someDay))

    def 'should create trip after choosing destination and period'() {
        given:
            TravelerId travelerId = new TravelerId()
        when:
            tripService.create(travelerId, "Los Angeles", someDay, someDay.plusDays(5))
        then:
            Optional<Trip> trip = tripRepository.findByTraveler(travelerId)
            trip.isPresent()
            trip.get().events()*.class == [TripCreated]
    }

    def 'can start trip after plan assignment'() {
        given:
            TravelerId travelerId = new TravelerId()
            PlanDetails planDetails = new PlanDetails()
        and:
            tripService.create(travelerId, "Madrid", someDay, someDay.plusDays(5))
        and:
            Trip trip = tripRepository.findByTraveler(travelerId).get()
        when:
            tripService.assignPlan(trip.tripId, planDetails)
        then:
            trip.start(someDay).isSuccessful()
            trip.events()*.class == [TripCreated, TripStarted]
    }

    def 'can finish trip if it is started'() {
        given:
            TravelerId travelerId = new TravelerId()
        and:
            tripService.create(travelerId, "London", someDay, someDay.plusDays(2))
        and:
            Trip trip = tripRepository.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, new PlanDetails())
        when:
            trip.start(someDay)
        then:
            trip.finish().isSuccessful()
            trip.events()*.class == [TripCreated, TripStarted, TripFinished]
    }

    def 'can share trip if it is finished'() {
        given:
            TravelerId travelerId = new TravelerId()
        and:
            tripService.create(travelerId, "London", someDay, someDay.plusDays(2))
        and:
            Trip trip = tripRepository.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, new PlanDetails())
        and:
            tripService.start(trip.tripId)
        when:
            trip.finish()
        then:
            trip.share().isSuccessful()
            trip.events()*.class == [TripCreated, TripStarted, TripFinished, TripShared]
    }
}
