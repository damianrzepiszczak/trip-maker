package rzepiszczak.damian.tripmaker.trip.management.ui.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.AssignPlanCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.CreateNewTripCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripService;
import rzepiszczak.damian.tripmaker.trip.management.readmodel.TravelerTrips;
import rzepiszczak.damian.tripmaker.trip.management.readmodel.TripsView;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class TripsController {

    private final TripService tripService;
    private final TripsView tripsView;

    @GetMapping("/travelers/{travelerId}/trips")
    ResponseEntity<TravelerTrips> travelerTrips(@PathVariable String travelerId) {
        return ResponseEntity.ok(tripsView.findTravelerTrips(TravelerId.from(travelerId)));
    }

    @GetMapping("/travelers/{travelerId}/trips/{tripId}")
    ResponseEntity<TravelerTrips.Trip> travelerTrip(@PathVariable String travelerId, @PathVariable String tripId) {
        return ResponseEntity.ok(tripsView.findByTripId(TripId.from(tripId)));
    }

    @PostMapping("/travelers/{travelerId}/trips")
    ResponseEntity<Void> createTrip(@PathVariable String travelerId, @RequestBody CreateTripRequest createTripRequest) {
        CreateNewTripCommand createNewTripCommand = new CreateNewTripCommand(
                TravelerId.from(createTripRequest.getTravelerId()),
                createTripRequest.getDestination(),
                createTripRequest.getFrom(),
                createTripRequest.getTo());
        TripId tripId = tripService.create(createNewTripCommand);
        return ResponseEntity.created(URI.create("/travelers/" + travelerId + "/trips/" + tripId.getId())).build();
    }

    @PostMapping("/trips/{tripId}/plans")
    void assignPlan(@PathVariable String tripId) {
        tripService.assignPlan(new AssignPlanCommand(TripId.from(UUID.fromString(tripId))));
    }
}
