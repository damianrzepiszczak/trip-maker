package rzepiszczak.damian.tripmaker.trip.domain.advisor;

import rzepiszczak.damian.tripmaker.trip.domain.advisor.model.Advisor;

public interface Advisors {
    void save(Advisor advisor);
    Advisor findByTripId(String tripId);
}
