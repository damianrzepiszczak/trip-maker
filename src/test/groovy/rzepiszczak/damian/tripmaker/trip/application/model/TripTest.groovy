package rzepiszczak.damian.tripmaker.trip.application.model

import rzepiszczak.damian.tripmaker.common.exception.DomainException
import rzepiszczak.damian.tripmaker.trip.application.model.events.*
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.Month

class TripTest extends Specification {

    private LocalDate from = LocalDate.of(2024, Month.MAY, 3)
    @Subject
    private Trip trip = new Trip(TripId.from(UUID.randomUUID()), TravelerId.from(UUID.randomUUID()), Destination.of("Madeira"), Period.from(from, from.plusDays(2)))

    def 'can start max one day before from date'() {
        given: 'new timeline created'
            trip.assignTimeline(new Timeline(List.of()))
        when: 'start trip one day before'
            trip.start(from.minusDays(1))
        then: 'trip was created and started'
            trip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'cannot start if more than one day before from'() {
        when:
            trip.start(from.minusDays(2))
        then:
            DomainException exception = thrown()
            exception.message == "Cannot start trip, assign plan or check possible start date"
    }

    def 'can start if timeline assigned'() {
        given:
            Timeline plan = new Timeline(List.of())
        and:
            trip.assignTimeline(plan)
        when:
            trip.start(from)
        then:
            trip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted]
    }

    def 'cannot start if timeline not assigned'() {
        when:
            trip.start(from)
        then:
            DomainException exception = thrown()
            exception.message == "Cannot start trip, assign plan or check possible start date"
    }

    def 'can finish started trip'() {
        given:
            trip.assignTimeline(new Timeline(List.of()))
        and:
            trip.start(from)
        expect:
            trip.finish()
            trip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished]
    }

    def 'cannot finish not started trip'() {
        when:
            trip.finish()
        then:
            DomainException exception = thrown()
            exception.message == "Cannot finish not started trip"
    }

    def 'should cancel not started trip'() {
        when:
            trip.cancel()
        then:
            trip.domainEvents()*.class == [TripCreated, TripCanceled]
    }

    def 'should not cancel started trip'() {
        given:
            trip.assignTimeline(new Timeline(List.of()))
        when:
            trip.start(from)
            trip.cancel()
        then:
            DomainException exception = thrown()
            exception.message == "Cannot cancel started trip"
    }

    def 'can share finished trip'() {
        given:
            trip.assignTimeline(new Timeline(List.of()))
        and:
            trip.start(from)
        and:
            trip.finish()
        when:
            trip.share()
        then:
            trip.domainEvents()*.class == [TripCreated, TimelineCreated, TripStarted, TripFinished, TripShared]
    }

    def 'cannot share not finished trip'() {
        given:
            trip.assignTimeline(new Timeline(List.of()))
        and:
            trip.start(from)
        when:
            trip.share()
        then:
            DomainException exception = thrown()
            exception.message == "Cannot share not finished trip"
    }

    def 'cannot reschedule trip period if different amount of days'() {
        given:
            LocalDate newFrom = LocalDate.of(2024, 5, 27)
            Period newPeriod = Period.from(newFrom, newFrom.plusDays(3))
        when:
            trip.reschedule(newPeriod)
        then:
            DomainException exception = thrown()
            exception.message == "New Period has different amount of days"
    }
}