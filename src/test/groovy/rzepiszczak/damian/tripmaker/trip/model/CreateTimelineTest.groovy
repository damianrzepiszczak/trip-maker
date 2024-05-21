package rzepiszczak.damian.tripmaker.trip.model

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.traveler.TravelerId
import rzepiszczak.damian.tripmaker.trip.application.AssignPlanCommand
import rzepiszczak.damian.tripmaker.trip.model.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.model.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.model.events.TripStarted
import rzepiszczak.damian.tripmaker.trip.infrastructure.TripConfiguration
import spock.lang.Specification

import java.time.LocalDateTime

class CreateTimelineTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private TripConfiguration configuration = new TripConfiguration()
    private Trips trips = configuration.tripRepository()
    private TripService tripService = new TripService(trips, new MockClock(someDay))

    def 'create new timeline based on plan details'() {
        given: 'traveler want to organize Dubai trip based on sample plan details'
            TravelerId travelerId = new TravelerId()
            AssignPlanCommand assignPlanCommand = new AssignPlanCommand()
            assignPlanCommand.addDetail(someDay, new AssignPlanCommand.DayInformation("First day activities", List.of("Deira")))
        and:
            tripService.create(travelerId, "Dubai", someDay, someDay.plusDays(1))
        when: 'assign plan'
        Trip trip = trips.findByTraveler(travelerId).get()
            tripService.assignPlan(trip.tripId, assignPlanCommand)
        then: 'timeline was created and trip can be started'
            trip.start(someDay).isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }
}
