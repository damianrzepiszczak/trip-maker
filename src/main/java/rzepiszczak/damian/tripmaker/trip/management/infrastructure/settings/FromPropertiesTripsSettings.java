package rzepiszczak.damian.tripmaker.trip.management.infrastructure.settings;

import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripsSettings;

class FromPropertiesTripsSettings implements TripsSettings {
    @Override
    public int allowedNumberOfTrips() {
        return 5;
    }
}
