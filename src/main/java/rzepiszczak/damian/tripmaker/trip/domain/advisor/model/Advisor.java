package rzepiszczak.damian.tripmaker.trip.domain.advisor.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Advisor {
    private final AdvisorId advisorId;
    private final String tripId;
    private final List<Hint> hints = new ArrayList<>();

    public void generateWelcomeHint() {
        hints.add(new Hint(Hint.Type.WELCOME));
    }

    public void generateDailyHint(LocalDate day) {
        hints.add(new Hint(Hint.Type.DAILY));
    }

    void generateSummary() {
        hints.add(new Hint(Hint.Type.SUMMARY));
    }
}
