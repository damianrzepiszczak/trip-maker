package rzepiszczak.damian.tripmaker.trip.hints.application.domain

import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId
import rzepiszczak.damian.tripmaker.trip.management.application.domain.events.TripCreated
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class AssistanceTest extends Specification {

    private LocalDate someDay = LocalDate.of(2024, 06, 11)
    @Subject
    private Assistance assistance = new Assistance(AssistanceId.from(UUID.randomUUID()), TripId.from(UUID.randomUUID()))

    def 'should create initial hint for newly created trip'() {
        given: 'new trip created'
            TripCreated tripCreated = new TripCreated(UUID.randomUUID().toString(), someDay, someDay.plusDays(3))
        when: 'generate hint'
            assistance.publishHint(new Hint("Initial hint", tripCreated.from))
        then: 'initial hint was generated'
            assistance.hints().size() == 1
            assistance.hints()[0].getContent() == "Initial hint"
    }
}
