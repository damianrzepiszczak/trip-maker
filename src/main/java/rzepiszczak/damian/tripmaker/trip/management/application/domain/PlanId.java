package rzepiszczak.damian.tripmaker.trip.management.application.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanId {
    private String id;
    static PlanId from(UUID id) {
        return new PlanId(id.toString());
    }
}
