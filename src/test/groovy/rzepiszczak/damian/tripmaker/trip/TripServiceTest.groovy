package rzepiszczak.damian.tripmaker.trip

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.traveler.TravelerId
import rzepiszczak.damian.tripmaker.trip.dto.CreateTimelineRequest
import rzepiszczak.damian.tripmaker.trip.events.*
import spock.lang.Specification

import java.time.LocalDateTime

class TripServiceTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private Trips trips = new InMemoryTripRepository()
    private TripService tripService = new TripService(trips, new MockClock(someDay))

    def 'should create trip after choosing destination and period'() {
        given: 'traveler wants to organize new trip'
            TravelerId travelerId = new TravelerId()
        when: 'for given destination and period create trip'
            tripService.create(travelerId, "Los Angeles", someDay, someDay.plusDays(5))
        then: 'trip was created'
            Optional<Trip> trip = trips.findByTraveler(travelerId)
            trip.isPresent()
            trip.get().events()*.class == [TripCreated]
    }

    def 'can start trip after plan assignment'() {
        given: 'traveler wants to create trip based on plan'
            TravelerId travelerId = new TravelerId()
            CreateTimelineRequest request = new CreateTimelineRequest()
        and:
            tripService.create(travelerId, "Madrid", someDay, someDay.plusDays(5))
        and:
            Trip trip = trips.findByTraveler(travelerId).get()
        when: 'create new timeline based on plan'
            tripService.assignPlan(trip.tripId, request)
        then: 'trip can be started'
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
            tripService.assignPlan(trip.tripId, new CreateTimelineRequest())
        when:
            trip.start(someDay)
        then: 'traveler can finish trip because is started'
            trip.finish().isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished]
    }

    def 'can share trip if it is finished'() {
        given: 'traveler wants to complete trip and share'
            TravelerId travelerId = new TravelerId()
        and:
            tripService.create(travelerId, "London", someDay, someDay.plusDays(2))
        and:
            Trip trip = trips.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, new CreateTimelineRequest())
        and:
            tripService.start(trip.tripId)
        when: 'traveler finishes trip'
            trip.finish()
        then: 'trip details can be shared after finishing it'
            trip.share().isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished, TripShared]
    }
}
