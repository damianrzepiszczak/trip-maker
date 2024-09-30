package rzepiszczak.damian.tripmaker.planning.domain.model;

import lombok.Getter;

class Plan {

    private PlanId planId;
    @Getter
    private PlanningStage planningStage;
    @Getter
    private PlanningType planningType;
    private String destination;
    private int travelersCount;
    private int days;

    Plan(PlanId planId, String destination, int travelersCount, int days) {
        this.planId = planId;
        this.destination = destination;
        this.travelersCount = travelersCount;
        this.days = days;
        this.planningStage = PlanningStage.IN_PROGRESS;
        this.planningType = PlanningType.MANUAL;
    }
}
