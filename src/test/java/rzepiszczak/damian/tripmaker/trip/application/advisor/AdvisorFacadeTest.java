package rzepiszczak.damian.tripmaker.trip.application.advisor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.Advisors;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.model.Advisor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdvisorFacadeTest {

    @Mock
    public Advisors advisors;

    @InjectMocks
    private AdvisorFacade advisorFacade;

    @Test
    void shouldCreateAdvisor() {
        // given
        String tripId = "tripId";

        // when
        advisorFacade.createAdvisor(tripId);

        // then
        verify(advisors).save(any(Advisor.class));
    }
}