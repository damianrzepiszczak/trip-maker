package rzepiszczak.damian.tripmaker.trip.application.model

import rzepiszczak.damian.tripmaker.common.exception.DomainException
import rzepiszczak.damian.tripmaker.trip.application.model.events.*
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.Month

class TripTest extends Specification {

    private LocalDateTime from = LocalDateTime.of(2024, Month.MAY, 3, 0, 0)
    @Subject
    private Trip trip = new Trip(TripId.from(UUID.randomUUID()), TravelerId.from(UUID.randomUUID()), Destination.of("Madeira"), Period.from(from, from.plusDays(2)))

    def 'can start max one day before from date'() {
        given: 'new timeline created'
            trip.assign(new Timeline(PlanId.from(UUID.randomUUID())))
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
            Timeline plan = new Timeline(PlanId.from(UUID.randomUUID()))
        and:
            trip.assign(plan)
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
            trip.assign(new Timeline(PlanId.from(UUID.randomUUID())))
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
            trip.assign(new Timeline(PlanId.from(UUID.randomUUID())))
        when:
            trip.start(from)
            trip.cancel()
        then:
            DomainException exception = thrown()
            exception.message == "Cannot cancel started trip"
    }

    def 'can share finished trip'() {
        given:
            trip.assign(new Timeline(PlanId.from(UUID.randomUUID())))
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
            trip.assign(new Timeline(PlanId.from(UUID.randomUUID())))
        and:
            trip.start(from)
        when:
            trip.share()
        then:
            DomainException exception = thrown()
            exception.message == "Cannot share not finished trip"
    }
}