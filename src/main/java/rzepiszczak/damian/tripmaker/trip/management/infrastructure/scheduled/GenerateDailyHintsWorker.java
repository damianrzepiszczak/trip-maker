package rzepiszczak.damian.tripmaker.trip.management.infrastructure.scheduled;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.trip.management.application.model.HintsGenerationService;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GenerateDailyHintsWorker {

    private final Clock clock;
    private final HintsGenerationService hintsGenerationService;

    @Scheduled(cron = "0 0 0 1/1 * ? *")
    void atMidnight() {
        hintsGenerationService.generate(clock.now());
    }
}
