package rzepiszczak.damian.tripmaker.common;

import java.time.LocalDate;

public record MockClock(LocalDate now) implements Clock {
}
