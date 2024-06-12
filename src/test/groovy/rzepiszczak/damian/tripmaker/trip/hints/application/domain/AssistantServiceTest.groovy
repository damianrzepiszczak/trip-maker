package rzepiszczak.damian.tripmaker.trip.hints.application.domain

import rzepiszczak.damian.tripmaker.common.MockClock
import rzepiszczak.damian.tripmaker.trip.hints.infractructure.AssistantsPersistenceConfiguration
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId
import rzepiszczak.damian.tripmaker.trip.management.application.domain.events.TripCreated
import spock.lang.Specification

import java.time.LocalDate

class AssistantServiceTest extends Specification {

    private LocalDate someDay = LocalDate.of(2024, 06, 11)
    private AssistantsPersistenceConfiguration assistantsPersistence = new AssistantsPersistenceConfiguration()
    private HintsGenerator hintsGenerator = new HintsGenerator(new MockClock(LocalDate.now()))
    private Assistants assistants = assistantsPersistence.assistantsRepository()
    private AssistanceService assistanceService = new AssistanceFacade(hintsGenerator, assistants)

    def 'should create initial hint for newly created trip'() {
        given:
            String tripId = UUID.randomUUID().toString()
            TripCreated tripCreated = new TripCreated(tripId, someDay, someDay.plusDays(3), "Paris")
        when: 'new trip was created'
            assistanceService.generateInitialHint(tripCreated)
        then: 'initial hint was generated'
            Assistant assistant = assistants.findTripAssistant(TripId.from(tripId)).get()
            assistant.hints().size() == 1
            assistant.hints()[0].content == "New trip to Paris was created. We will push new hints soon to improve your dreams"
    }
}
