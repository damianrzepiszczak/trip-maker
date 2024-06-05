package rzepiszczak.damian.tripmaker.trip.hints.application.model;

import rzepiszczak.damian.tripmaker.common.AggregateRoot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assistant extends AggregateRoot<AssistantId> {

    private final LocalDate assistFrom;
    private final LocalDate assistTo;
    private final LocalDate tripStart;
    private final LocalDate tripEnd;
    private final List<Hint> hints;

    Assistant(AssistantId assistantId, LocalDate tripStart, LocalDate tripEnd){
        this.hints = new ArrayList<>();
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.id = assistantId;
        assistFrom = tripStart.minusDays(5);
        assistTo = tripEnd.plusDays(5);
        hints.add(new Hint());
    }

    void generateInitialHint(LocalDate now){
        if (assistFrom.equals(now)) {
            hints.add(new Hint());
        }
    }

    List<Hint> hints() {
        return Collections.unmodifiableList(hints);
    }
}
