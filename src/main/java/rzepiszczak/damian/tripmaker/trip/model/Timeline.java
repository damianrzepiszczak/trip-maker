package rzepiszczak.damian.tripmaker.trip.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.planning.PlanId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
class Timeline {

    private final PlanId planId;
    private final List<DayActivity> activities = new ArrayList<>();

    void assignDayActivity(DayActivity activity) {
        activities.add(activity);
    }

    List<DayActivity> activities() {
        return Collections.unmodifiableList(activities);
    }
}
