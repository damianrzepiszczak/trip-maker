package rzepiszczak.damian.tripmaker.trip.hints.application.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class CreateNewAssistantCommand {
    private final String tripId;
    private final LocalDate tripStart;
    private final LocalDate tripEnd;
}
