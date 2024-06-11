package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AssistanceId {
    private String id;
    public static AssistanceId from(UUID id) {
        return new AssistanceId(id.toString());
    }
}
