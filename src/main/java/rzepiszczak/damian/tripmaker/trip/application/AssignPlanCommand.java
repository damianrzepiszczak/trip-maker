package rzepiszczak.damian.tripmaker.trip.application;

import lombok.Getter;
import rzepiszczak.damian.tripmaker.planning.PlanId;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AssignPlanCommand {
    private PlanId planId;
    private final Map<LocalDateTime, DayInformation> details = new HashMap<>();

    public void addDetail(LocalDateTime time, DayInformation information) {
        details.put(time, information);
    }

    public record DayInformation(String note, List<String> attractions) {
    }
}
