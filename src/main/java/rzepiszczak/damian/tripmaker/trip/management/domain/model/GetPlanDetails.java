package rzepiszczak.damian.tripmaker.trip.management.domain.model;

import rzepiszczak.damian.tripmaker.trip.management.application.commands.DayInformation;

import java.time.LocalDate;
import java.util.Map;

public interface GetPlanDetails {
    Map<LocalDate, DayInformation> get(PlanId planId);
}
