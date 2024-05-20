package rzepiszczak.damian.tripmaker.common;

import java.time.LocalDateTime;

public record MockClock(LocalDateTime now) implements Clock {
}
