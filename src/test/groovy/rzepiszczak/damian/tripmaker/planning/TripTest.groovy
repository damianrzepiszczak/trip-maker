package rzepiszczak.damian.tripmaker.planning

import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.Month

class TripTest extends Specification {

    private LocalDateTime from = LocalDateTime.of(2024, Month.MAY, 3, 0, 0)
    @Subject
    private Trip trip = TripFactory.create("Madeira", from, from.plusDays(2))

    def 'can start max one day before from date'() {
        given:
            trip.assign(new Timeline())
        expect:
            trip.start(from.minusDays(1)).isSuccessful()
        and:
            trip.events()[0] instanceof NewTripCreated
    }

    def 'cannot start if more than one day before from'() {
        expect:
            trip.start(from.minusDays(2)).isFailure()
    }

    def 'can start if timeline assigned'() {
        given:
            Timeline plan = new Timeline()
        and:
            trip.assign(plan)
        expect:
            trip.start(from).isSuccessful()
    }

    def 'cannot start if timeline not assigned'() {
        expect:
            trip.start(from).isFailure()
    }

    def 'can finish started trip'() {
        given:
            trip.assign(new Timeline())
        and:
            trip.start(from)
        expect:
            trip.finish().isSuccessful()
    }

    def 'cannot finish not started trip'() {
        expect:
            trip.finish().isFailure()
    }

    def 'should cancel not started trip'() {
        expect:
            trip.cancel().isSuccessful()
    }

    def 'should not cancel started trip'() {
        given:
            trip.assign(new Timeline())
        and:
            trip.start(from)
        expect:
            trip.cancel().isFailure()
    }

    def 'can share finished trip'() {
        given:
            trip.assign(new Timeline())
        and:
            trip.start(from)
        and:
            trip.finish()
        expect:
            trip.share().isSuccessful()
    }
}