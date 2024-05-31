package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.AllArgsConstructor;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class Timeline {

    private final List<DayActivity> activities;

    Timeline(List<DayActivity> activities) {
        this.activities = activities;
    }

    void modifyNote(LocalDate day, String note) {
        Optional<DayActivity> found = activities.stream()
                .filter(dayActivity -> dayActivity.day.equals(day))
                .findFirst();
        DayActivity activity = found.orElseThrow(() -> new DomainException("Cannot find " + day + " activity"));
        activity.note = note;
    }

    List<DayActivity> activities() {
        return Collections.unmodifiableList(activities);
    }

    @AllArgsConstructor
    static class DayActivity {
        private final LocalDate day;
        private final List<String> attractions;
        private String note;
    }
}
