package rzepiszczak.damian.tripmaker.trip.domain.advisor.model

import rzepiszczak.damian.tripmaker.trip.application.advisor.commands.GenerateDailyHintCommand
import rzepiszczak.damian.tripmaker.trip.domain.management.model.events.TripCreated
import spock.lang.Specification

import java.time.LocalDate

class AdvisorTest extends Specification {

    def 'should generate initial hint whenever new trip created'() {
        given:
            TripCreated tripCreated = new TripCreated(UUID.randomUUID().toString(), LocalDate.of(2024, 12, 6), LocalDate.of(2024, 12, 12))
            Advisor advisor = new Advisor(AdvisorId.from(UUID.randomUUID()),tripCreated.tripId())
        when:
            advisor.generateWelcomeHint()
        then:
            advisor.hints[0].type() == Hint.Type.WELCOME
    }

    def 'should generate daily hint'() {
        given: 'trip created and welcome hint generated'
            LocalDate tripStartDay = LocalDate.of(2024, 12, 6)
            TripCreated tripCreated = new TripCreated(UUID.randomUUID().toString(), tripStartDay, tripStartDay.plusDays(6))
            Advisor advisor = new Advisor(AdvisorId.from(UUID.randomUUID()), tripCreated.tripId())
            advisor.generateWelcomeHint()
        when: 'new day starts'
            GenerateDailyHintCommand generateDailyHintCommand = new GenerateDailyHintCommand(tripStartDay.plusDays(1))
            advisor.generateDailyHint(generateDailyHintCommand.day())
        then: 'daily hint is generated'
            advisor.hints.size() == 2
    }

    def 'should generate finial hint whenever trip ended'() {
        given: 'trip which ends soon'
            String tripId = UUID.randomUUID().toString()
            LocalDate tripStartDay = LocalDate.of(2024, 12, 6)
            TripCreated tripCreated = new TripCreated(tripId, tripStartDay, tripStartDay.plusDays(6))
            Advisor advisor = new Advisor(AdvisorId.from(UUID.randomUUID()), tripCreated.tripId())
            advisor.generateWelcomeHint()
        when: 'trip ended'
            advisor.generateSummary()
        then: 'final hint and sum up generated'
            advisor.hints.size() == 2
    }
}
