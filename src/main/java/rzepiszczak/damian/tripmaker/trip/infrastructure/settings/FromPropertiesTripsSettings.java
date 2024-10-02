package rzepiszczak.damian.tripmaker.trip.infrastructure.settings;

import rzepiszczak.damian.tripmaker.trip.domain.management.model.TripsSettings;

class FromPropertiesTripsSettings implements TripsSettings {
    @Override
    public int allowedNumberOfTrips() {
        return 5;
    }
}
