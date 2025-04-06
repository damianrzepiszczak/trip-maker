package rzepiszczak.damian.tripmaker.trip.application.advisor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.AdvisorService;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.events.TripCreated;

@Slf4j
@RequiredArgsConstructor
public class TripEventsHandler {

    private final AdvisorService advisorService;

    @EventListener
    @Transactional
    public void handleTripCreatedEvent(TripCreated tripCreated) {
        log.info("Handling TripCreated event for tripId: {}", tripCreated.tripId());
        advisorService.createAdvisor(tripCreated.tripId());
    }
}
