package rzepiszczak.damian.tripmaker.trip

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.planning.PlanDetails
import rzepiszczak.damian.tripmaker.traveler.TravelerId
import rzepiszczak.damian.tripmaker.trip.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.events.TripFinished
import rzepiszczak.damian.tripmaker.trip.events.TripShared
import rzepiszczak.damian.tripmaker.trip.events.TripStarted
import spock.lang.Specification

import java.time.LocalDateTime

class TripServiceTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private Trips trips = new InMemoryTripRepository()
    private TripService tripService = new TripService(trips, new MockClock(someDay))

    def 'should create trip after choosing destination and period'() {
        given:
            TravelerId travelerId = new TravelerId()
        when:
            tripService.create(travelerId, "Los Angeles", someDay, someDay.plusDays(5))
        then:
            Optional<Trip> trip = trips.findByTraveler(travelerId)
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
            Trip trip = trips.findByTraveler(travelerId).get()
        when:
            tripService.assignPlan(trip.tripId, planDetails)
        then:
            trip.start(someDay).isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'can finish trip if it is started'() {
        given:
            TravelerId travelerId = new TravelerId()
        and:
            tripService.create(travelerId, "London", someDay, someDay.plusDays(2))
        and:
            Trip trip = trips.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, new PlanDetails())
        when:
            trip.start(someDay)
        then:
            trip.finish().isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished]
    }

    def 'can share trip if it is finished'() {
        given:
            TravelerId travelerId = new TravelerId()
        and:
            tripService.create(travelerId, "London", someDay, someDay.plusDays(2))
        and:
            Trip trip = trips.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, new PlanDetails())
        and:
            tripService.start(trip.tripId)
        when:
            trip.finish()
        then:
            trip.share().isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished, TripShared]
    }
}
