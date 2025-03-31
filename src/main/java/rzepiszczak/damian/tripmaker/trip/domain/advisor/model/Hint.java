package rzepiszczak.damian.tripmaker.trip.domain.advisor.model;

public record Hint(Hint.Type type) {
    public enum Type {
        WELCOME, DAILY, SUMMARY
    }
}
