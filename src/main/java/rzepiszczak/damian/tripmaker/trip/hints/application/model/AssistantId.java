package rzepiszczak.damian.tripmaker.trip.hints.application.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssistantId {
    private String id;

    public static AssistantId from(UUID id) {
        return new AssistantId(id.toString());
    }
}
