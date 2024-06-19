package rzepiszczak.damian.tripmaker.trip.management.ui.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rzepiszczak.damian.tripmaker.trip.management.application.commands.CreateNewTripCommand;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripService;
import rzepiszczak.damian.tripmaker.trip.management.readmodel.TravelerTrips;
import rzepiszczak.damian.tripmaker.trip.management.readmodel.TripsView;

import java.net.URI;

@Tag(name = "Trips", description = "Trips REST Api under development")
@RestController
@RequiredArgsConstructor
class TripsController {

    private final TripService tripService;
    private final TripsView tripsView;

    @Operation(summary = "Get list of traveler trips")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found list of trips", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/travelers/{travelerId}/trips", produces = "application/vnd.trips.app-v1+json")
    ResponseEntity<TravelerTrips> travelerTrips(@PathVariable String travelerId) {
        return ResponseEntity.ok(tripsView.findTravelerTrips(TravelerId.from(travelerId)));
    }

    @Operation(summary = "Get details of traveler trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found trip details", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/travelers/{travelerId}/trips/{tripId}", produces = "application/vnd.trips.app-v1+json")
    ResponseEntity<TravelerTrips.Trip> travelerTrip(@PathVariable String travelerId, @PathVariable String tripId) {
        return ResponseEntity.ok(tripsView.findByTripId(TripId.from(tripId)));
    }

    @Operation(summary = "Create new trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New trip was created")
    })
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
}
