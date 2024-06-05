package rzepiszczak.damian.tripmaker.trip.hints.application

import rzepiszczak.damian.tripmaker.trip.hints.infrastructure.AssistanceRepositoryConfiguration
import spock.lang.Specification

import java.time.LocalDate

class AssistantServiceTest extends Specification {

    private LocalDate tripStart = LocalDate.of(2024, 06, 05)
    private AssistanceRepositoryConfiguration assistanceRepositoryConfiguration = new AssistanceRepositoryConfiguration()
    private Assistants assistants = assistanceRepositoryConfiguration.assistantsRepository()
    private AssistantService assistantService = new AssistantService(assistants)

    def 'should create assistance helper and initial hint if new trip created'() {
        given: 'for newly trip created'
            CreateNewAssistantCommand createNewAssistantCommand = new CreateNewAssistantCommand(UUID.randomUUID().toString(), tripStart, tripStart.plusDays(5))
        when: 'generate initial hint'
            AssistantId assistantId = assistantService.createAssistanceForNewTrip(createNewAssistantCommand)
        then: 'initial hint generated'
            Assistant assistant = assistants.findTripAssistant(assistantId).get()
            assistant.hints().size() == 1
    }
}
