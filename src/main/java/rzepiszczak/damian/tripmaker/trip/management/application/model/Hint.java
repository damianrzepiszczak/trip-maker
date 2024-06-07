package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
class Hint {
    private final String content;
    private final LocalDate whenToShow;
}
