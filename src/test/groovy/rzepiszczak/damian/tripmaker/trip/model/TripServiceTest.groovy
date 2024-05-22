package rzepiszczak.damian.tripmaker.trip.model

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.traveler.TravelerId
import rzepiszczak.damian.tripmaker.trip.application.AssignPlanCommand
import rzepiszczak.damian.tripmaker.trip.infrastructure.TripConfiguration
import rzepiszczak.damian.tripmaker.trip.model.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.model.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.model.events.TripFinished
import rzepiszczak.damian.tripmaker.trip.model.events.TripShared
import rzepiszczak.damian.tripmaker.trip.model.events.TripStarted
import spock.lang.Specification

import java.time.LocalDateTime

class TripServiceTest extends Specification {

    private LocalDateTime someDay = LocalDateTime.of(2024, 5, 15, 0, 0)
    private TripConfiguration configuration = new TripConfiguration()
    private Trips trips = configuration.tripRepository()
    private TripService tripService = new TripService(trips, new MockClock(someDay), new TripFactory(trips))

    def 'should create trip after choosing destination and period'() {
        given: 'traveler wants to organize new trip'
            TravelerId travelerId = new TravelerId()
        when: 'for given destination and period create trip'
            tripService.create(travelerId, "Los Angeles", someDay, someDay.plusDays(5))
        then: 'trip was created'
            List<Trip> travelerTrips = trips.findTravelerTrips(travelerId)
            travelerTrips.size() == 1
            travelerTrips[0].events()*.class == [TripCreated]
    }

    def 'cannot create trip with the same destination'() {
        given: 'traveler creates trip to Dubai'
            TravelerId travelerId = new TravelerId()
            tripService.create(travelerId, "Dubai", someDay, someDay.plusDays(10))
        when: 'traveler creates second trip to Dubai'
            tripService.create(travelerId, "Dubai", someDay, someDay.plusDays(7))
        then: 'cannot create with the same destination'
            thrown IllegalStateException
    }

    def 'can create two trips with different destination'() {
        given: 'traveler creates trip to Dubai'
        TravelerId travelerId = new TravelerId()
        tripService.create(travelerId, "Dubai", someDay, someDay.plusDays(10))
        when: 'traveler creates second trip to Madeira'
        tripService.create(travelerId, "Madeira", someDay, someDay.plusDays(7))
        then: 'can create with the same destination'
        trips.findTravelerTrips(travelerId).size() == 2
    }

    def 'can start trip after plan assignment'() {
        given: 'traveler wants to create trip based on plan'
            TravelerId travelerId = new TravelerId()
            AssignPlanCommand assignPlanCommand = new AssignPlanCommand()
        and:
            tripService.create(travelerId, "Madrid", someDay, someDay.plusDays(5))
        and:
            Trip madridTrip = trips.findTravelerTrips(travelerId)[0]
        when: 'create new timeline based on plan'
            tripService.assignPlan(madridTrip.tripId, assignPlanCommand)
        then: 'trip can be started'
            madridTrip.start(someDay).isSuccessful()
            madridTrip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'can finish trip if it is started'() {
        given:
            TravelerId travelerId = new TravelerId()
        and:
            tripService.create(travelerId, "London", someDay, someDay.plusDays(2))
        and:
            Trip trip = trips.findTravelerTrips(travelerId)[0]
            tripService.assignPlan(trip.tripId, new AssignPlanCommand())
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
            Trip trip = trips.findTravelerTrips(travelerId)[0]
            tripService.assignPlan(trip.tripId, new AssignPlanCommand())
        and:
            tripService.start(trip.tripId)
        when: 'traveler finishes trip'
            trip.finish()
        then: 'trip details can be shared after finishing it'
            trip.share().isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished, TripShared]
    }
}
