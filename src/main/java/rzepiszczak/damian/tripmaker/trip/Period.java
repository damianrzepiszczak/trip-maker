package rzepiszczak.damian.tripmaker.trip;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Period {

    private LocalDateTime from;
    private LocalDateTime to;

    public static Period from(LocalDateTime from, LocalDateTime to) {
        return new Period(from, to);
    }
}
