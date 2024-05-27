package rzepiszczak.damian.tripmaker.trip.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rzepiszczak.damian.tripmaker.trip.application.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.model.TripFacade;
import rzepiszczak.damian.tripmaker.trip.model.TripId;

@RestController
@RequiredArgsConstructor
class TripController {

    private final TripFacade facade;

    @PostMapping("/trips/{tripId}/plans")
    public void assignPlan(@PathVariable String tripId) {
        facade.assignPlan(new AssignPlanCommand(new TripId()));
    }
}
