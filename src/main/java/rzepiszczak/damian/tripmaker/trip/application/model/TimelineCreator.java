package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TimelineCreator {

    static Timeline create(AssignPlanCommand request) {
        List<Timeline.DayActivity> activities = new ArrayList<>();
        request.getDetails().forEach((day, information) -> activities.add(new Timeline.DayActivity(day, information.attractions(), information.note())));
        return new Timeline(activities);
    }
}
