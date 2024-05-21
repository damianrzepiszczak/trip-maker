package rzepiszczak.damian.tripmaker.trip

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.traveler.TravelerId
import rzepiszczak.damian.tripmaker.trip.dto.CreateTimelineRequest
import rzepiszczak.damian.tripmaker.trip.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.events.TripStarted
import spock.lang.Specification

import java.time.LocalDateTime

class CreateTimelineTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private Trips trips = new InMemoryTripRepository()
    private TripService tripService = new TripService(trips, new MockClock(someDay))

    def 'create new timeline based on plan details'() {
        given: 'traveler want to organize Dubai trip based on sample plan details'
            TravelerId travelerId = new TravelerId()
            CreateTimelineRequest request = new CreateTimelineRequest()
            request.addDetail(someDay, new CreateTimelineRequest.DayInformation("First day activities", List.of("Deira")))
        and:
            tripService.create(travelerId, "Dubai", someDay, someDay.plusDays(1))
        when: 'assign plan'
            Trip trip = trips.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, request)
        then: 'timeline was created and trip can be started'
            trip.start(someDay).isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }
}
