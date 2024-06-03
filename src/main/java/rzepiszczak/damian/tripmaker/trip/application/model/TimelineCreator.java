package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TimelineCreator {

    static Timeline create(AssignPlanCommand assignPlanCommand) {
        List<Timeline.TripDay> tripDays = new ArrayList<>();
        assignPlanCommand.getDetails()
                .forEach((day, information) -> tripDays.add(new Timeline.TripDay(day, information.note(), information.attractions())));
        return new Timeline(tripDays);
    }
}
