package rzepiszczak.damian.tripmaker.trip.application.advisor;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.AdvisorService;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.events.TripCreated;

@RequiredArgsConstructor
public class TripEventsHandler {

    private final AdvisorService advisorService;

    @Transactional
    @TransactionalEventListener
    public void handleTripCreatedEvent(TripCreated tripCreated) {
        advisorService.createAdvisor(tripCreated.tripId());
    }
}
