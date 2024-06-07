package rzepiszczak.damian.tripmaker.trip.management.ui.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.CreateNewTripCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.Trip;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripService;
import rzepiszczak.damian.tripmaker.trip.management.readmodel.TripsView;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class TripController {

    private final TripService tripService;
    private final TripsView tripsView;

    @GetMapping("/trips/{travelerId}")
    List<Trip> travelerTrips(@PathVariable String travelerId) {
        return tripsView.findTravelerTrips(TravelerId.from(travelerId));
    }

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
