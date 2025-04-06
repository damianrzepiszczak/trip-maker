package rzepiszczak.damian.tripmaker.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.Advisors;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TravelerId;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.Trips;
import rzepiszczak.damian.tripmaker.trip.api.rest.CreateTripRequest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TripE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Trips trips;

    @Autowired
    private Advisors advisors;

    @Test
    void shouldCreateTrip() {
        // given
        String travelerId = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";
        CreateTripRequest createTripRequest = new CreateTripRequest();
        createTripRequest.setDestination("Cyprus");
        createTripRequest.setTravelerId(travelerId);
        createTripRequest.setFrom(LocalDate.of(2023, 10, 1));
        createTripRequest.setTo(LocalDate.of(2023, 10, 10));

        // when
        restTemplate.postForEntity("/travelers/" + travelerId + "/trips", createTripRequest, Void.class);

        //then
        var tripsList = trips.findTravelerTrips(TravelerId.from(travelerId));
        assertThat(tripsList.size()).isEqualTo(1);

        var advisor = advisors.findByTripId(tripsList.getFirst().getId().id());
        assertThat(advisor).isNotNull();
    }
}
