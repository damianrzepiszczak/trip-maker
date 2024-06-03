package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
class TripDay {
    private LocalDate day;
    private String note;
    private final List<String> attractions;

    void changeDayDate(LocalDate newDate) {
        this.day = newDate;
    }

    void modifyNote(String note) {
        this.note = note;
    }

    void newDatAttraction(String attraction) {
        this.attractions.add(attraction);
    }
}
