package rzepiszczak.damian.tripmaker.trip.infrastructure.persistence.advisor;

import rzepiszczak.damian.tripmaker.trip.domain.advisor.Advisors;
import rzepiszczak.damian.tripmaker.trip.domain.advisor.model.Advisor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdvisorsLocalStore implements Advisors {

    private final Map<String, Advisor> advisors = new ConcurrentHashMap<>();

    @Override
    public void save(Advisor advisor) {
        advisors.put(advisor.getAdvisorId().id(), advisor);
    }

    @Override
    public Advisor findByTripId(String tripId) {
        return advisors.values().stream()
                .filter(advisor -> advisor.getTripId().equals(tripId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No advisor found for tripId: " + tripId));
    }
}
