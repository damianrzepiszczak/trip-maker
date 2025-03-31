package rzepiszczak.damian.tripmaker.trip.application.advisor;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.AdvisorService;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.Advisors;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.model.Advisor;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.model.AdvisorId;

import java.util.UUID;

@RequiredArgsConstructor
public class AdvisorFacade implements AdvisorService {

    private final Advisors advisors;

    @Override
    @Transactional
    public void createAdvisor(String tripId) {
        Advisor advisor = new Advisor(AdvisorId.from(UUID.randomUUID()), tripId);
        advisor.generateWelcomeHint();
        advisors.save(advisor);
    }
}
