package rzepiszczak.damian.tripmaker.trip.domain.advisor.model;

import java.util.UUID;

public record AdvisorId(String id) {
    public static AdvisorId from(UUID id) {
        return new AdvisorId(id.toString());
    }
}
