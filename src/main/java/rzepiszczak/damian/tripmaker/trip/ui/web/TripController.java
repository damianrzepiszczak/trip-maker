package rzepiszczak.damian.tripmaker.trip.ui.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rzepiszczak.damian.tripmaker.trip.application.model.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.application.model.TripId;
import rzepiszczak.damian.tripmaker.trip.application.model.TripService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
class TripController {

    private final TripService tripService;

    @PostMapping("/trips/{tripId}/plans")
    void assignPlan(@PathVariable String tripId) {
        tripService.assignPlan(new AssignPlanCommand(TripId.from(UUID.fromString(tripId))));
    }
}
