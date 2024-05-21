package rzepiszczak.damian.tripmaker.trip

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.planning.PlanDetails
import rzepiszczak.damian.tripmaker.traveler.TravelerId
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
        given: 'Dubai trip sample plan details'
            TravelerId travelerId = new TravelerId()
            PlanDetails details = new PlanDetails()
            details.addDetail(someDay, new PlanDetails.DayInformation("First day activities", List.of("Deira")))
        and:
            tripService.create(travelerId, "Dubai", someDay, someDay.plusDays(1))
        when: 'assign plan'
            Trip trip = trips.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, details)
        then: 'timeline was created and trip can be started'
            trip.start(someDay).isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }
}
