package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class Hint {
    private final String content;
    private final LocalDate whenToShow;
}
