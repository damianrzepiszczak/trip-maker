package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
class DayActivity {
    private final LocalDateTime time;
    private final String note;
    private final List<String> attractions;
}
