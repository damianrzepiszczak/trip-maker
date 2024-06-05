package rzepiszczak.damian.tripmaker.trip.management.ui.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripService;
import rzepiszczak.damian.tripmaker.trip.management.application.model.commands.CreateNewTripCommand;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
class TripController {

    private final TripService tripService;

    @PostMapping("/trips")
    void createTrip(@RequestBody CreateTripRequest createTripRequest) {
        CreateNewTripCommand createNewTripCommand = new CreateNewTripCommand(
                TravelerId.from(createTripRequest.getTravelerId()),
                createTripRequest.getDestination(),
                createTripRequest.getFrom(),
                createTripRequest.getTo());
        tripService.create(createNewTripCommand);
    }

    @PostMapping("/trips/{tripId}/plans")
    void assignPlan(@PathVariable String tripId) {
        tripService.assignPlan(new AssignPlanCommand(TripId.from(UUID.fromString(tripId))));
    }
}
