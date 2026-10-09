package net.java.cargotracker.modern.domain;

import java.time.Instant;
import java.util.Objects;

public record RouteSpecification(Location origin, Location destination, Instant arrivalDeadline) {

    public RouteSpecification {
        Objects.requireNonNull(origin, "Origin is required");
        Objects.requireNonNull(destination, "Destination is required");
        Objects.requireNonNull(arrivalDeadline, "Arrival deadline is required");
        if (origin.sameIdentityAs(destination)) {
            throw new IllegalArgumentException("Origin and destination can't be the same: " + origin);
        }
    }

    public boolean isSatisfiedBy(Itinerary itinerary) {
        return itinerary != null
                && origin.sameIdentityAs(itinerary.getInitialDepartureLocation())
                && destination.sameIdentityAs(itinerary.getFinalArrivalLocation())
                && arrivalDeadline.isAfter(itinerary.getFinalArrivalDate());
    }
}
