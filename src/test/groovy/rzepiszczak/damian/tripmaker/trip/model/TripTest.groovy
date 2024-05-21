package rzepiszczak.damian.tripmaker.trip.model

import rzepiszczak.damian.tripmaker.planning.PlanId
import rzepiszczak.damian.tripmaker.traveler.TravelerId
import rzepiszczak.damian.tripmaker.trip.model.events.TimelineCreated
import rzepiszczak.damian.tripmaker.trip.model.events.TripCanceled
import rzepiszczak.damian.tripmaker.trip.model.events.TripCreated
import rzepiszczak.damian.tripmaker.trip.model.events.TripFinished
import rzepiszczak.damian.tripmaker.trip.model.events.TripShared
import rzepiszczak.damian.tripmaker.trip.model.events.TripStarted
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.Month

class TripTest extends Specification {

    private LocalDateTime from = LocalDateTime.of(2024, Month.MAY, 3, 0, 0)
    @Subject
    private Trip trip = TripFactory.create(new TravelerId(), "Madeira", from, from.plusDays(2))

    def 'can start max one day before from date'() {
        given: 'new timeline created'
            trip.assign(new Timeline(new PlanId()))
        expect: 'start trip one day before'
            trip.start(from.minusDays(1)).isSuccessful()
        and: 'trip was created and started'
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'cannot start if more than one day before from'() {
        expect:
            trip.start(from.minusDays(2)).isFailure()
            trip.events()*.class == [TripCreated]
    }

    def 'can start if timeline assigned'() {
        given:
            Timeline plan = new Timeline(new PlanId())
        and:
            trip.assign(plan)
        expect:
            trip.start(from).isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'cannot start if timeline not assigned'() {
        expect:
            trip.start(from).isFailure()
            trip.events()*.class == [TripCreated]
    }

    def 'can finish started trip'() {
        given:
            trip.assign(new Timeline(new PlanId()))
        and:
            trip.start(from)
        expect:
            trip.finish().isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished]
    }

    def 'cannot finish not started trip'() {
        expect:
            trip.finish().isFailure()
            trip.events()*.class == [TripCreated]
    }

    def 'should cancel not started trip'() {
        expect:
            trip.cancel().isSuccessful()
            trip.events()*.class == [TripCreated, TripCanceled]
    }

    def 'should not cancel started trip'() {
        given:
            trip.assign(new Timeline(new PlanId()))
        and:
            trip.start(from)
        expect:
            trip.cancel().isFailure()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'can share finished trip'() {
        given:
            trip.assign(new Timeline(new PlanId()))
        and:
            trip.start(from)
        and:
            trip.finish()
        expect:
            trip.share().isSuccessful()
            trip.events()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished, TripShared]
    }
}