package rzepiszczak.damian.tripmaker.trip.domain.management.model;

import rzepiszczak.damian.tripmaker.trip.application.management.commands.DayInformation;

import java.time.LocalDate;
import java.util.Map;

public interface GetPlanDetails {
    Map<LocalDate, DayInformation> get(PlanId planId);
}
