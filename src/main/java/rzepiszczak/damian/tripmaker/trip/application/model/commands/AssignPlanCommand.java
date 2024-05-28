package rzepiszczak.damian.tripmaker.trip.application.model.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.planning.PlanId;
import rzepiszczak.damian.tripmaker.trip.application.model.TripId;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class AssignPlanCommand {
    private final TripId tripId;
    private PlanId planId;
    private final Map<LocalDateTime, DayInformation> details = new HashMap<>();

    public void addDetail(LocalDateTime time, DayInformation information) {
        details.put(time, information);
    }

    public record DayInformation(String note, List<String> attractions) {
    }
}
