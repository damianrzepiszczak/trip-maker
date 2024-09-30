package rzepiszczak.damian.tripmaker.planning.domain.model


import spock.lang.Specification

class PlanManagementTest extends Specification {

    def 'should initiate manual planning when basic data given'() {
        given: 'travelers count, destination and days count given'
            PlanId planId = PlanId.from(UUID.randomUUID())
            String destination = "Paris"
            int travelersCount = 2
            int days = 5
         when: 'start new travel plan'
            Plan plan = new Plan(planId, destination, travelersCount, days)
         then: 'manual planning started'
            plan.planningStage == PlanningStage.IN_PROGRESS
            plan.planningType == PlanningType.MANUAL
    }
}
