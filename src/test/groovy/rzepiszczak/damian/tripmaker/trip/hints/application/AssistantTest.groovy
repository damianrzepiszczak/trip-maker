package rzepiszczak.damian.tripmaker.trip.hints.application


import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class AssistantTest extends Specification {

    private LocalDate tripStart = LocalDate.of(2024, 06, 05)
    @Subject
    private Assistant assistant = new Assistant(AssistantId.from(UUID.randomUUID()), tripStart, tripStart.plusDays(5))

    def 'should generate initial hint if 5 days before trip start'(){
        when: 'try to generate initial trip hint'
            assistant.generateInitialHint(tripStart.minusDays(5))
        then: 'new hint generated before trip starts'
            List<Hint> hints = assistant.hints()
            hints.size() == 2
    }

    def 'should not generate initial hint if more than 5 days before trip start'(){
        when: 'try to generate initial trip hint'
            assistant.generateInitialHint(tripStart.minusDays(7))
        then: 'new hint generated before trip starts'
            List<Hint> hints = assistant.hints()
            hints.size() == 1
    }
}
