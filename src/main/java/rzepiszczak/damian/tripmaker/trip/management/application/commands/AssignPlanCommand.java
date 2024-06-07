package rzepiszczak.damian.tripmaker.trip.management.application.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.trip.management.application.model.PlanId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class AssignPlanCommand {
    private final TripId tripId;
    private PlanId planId;
    private final Map<LocalDate, DayInformation> details = new HashMap<>();

    public void addDetail(LocalDate time, DayInformation information) {
        details.put(time, information);
    }
}
