package rzepiszczak.damian.tripmaker.planning;

import java.util.UUID;

class ManualPlan {

    enum Stage {IN_PROGRESS, COMPLETE}

    private final String planId = UUID.randomUUID().toString();

    private Stage stage;

    ManualPlan() {
        this.stage = Stage.IN_PROGRESS;
    }
}
