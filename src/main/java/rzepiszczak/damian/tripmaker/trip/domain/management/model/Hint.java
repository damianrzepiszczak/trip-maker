package rzepiszczak.damian.tripmaker.trip.domain.management.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public
class Hint {
    private final String content;
    private final LocalDate whenToShow;
}
