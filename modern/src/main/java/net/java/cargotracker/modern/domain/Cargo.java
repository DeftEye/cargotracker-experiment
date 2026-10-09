package net.java.cargotracker.modern.domain;

import java.util.Objects;

/** Aggregate root. Identity is the tracking id. */
public final class Cargo {

    private final String trackingId;
    private final Location origin;
    private RouteSpecification routeSpecification;
    private Itinerary itinerary;
    private Delivery delivery;

    public Cargo(String trackingId, RouteSpecification routeSpecification) {
        this.trackingId = Objects.requireNonNull(trackingId, "Tracking ID is required");
        this.routeSpecification = Objects.requireNonNull(routeSpecification, "Route specification is required");
        this.origin = routeSpecification.origin();
        // Legacy derives the first delivery before it sets the empty itinerary,
        // so the itinerary argument is null here.
        this.delivery = Delivery.derivedFrom(routeSpecification, null, HandlingHistory.EMPTY);
        this.itinerary = Itinerary.EMPTY_ITINERARY;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Location getOrigin() {
        return origin;
    }

    public RouteSpecification getRouteSpecification() {
        return routeSpecification;
    }

    public Itinerary getItinerary() {
        return itinerary == null ? Itinerary.EMPTY_ITINERARY : itinerary;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void assignToRoute(Itinerary itinerary) {
        this.itinerary = Objects.requireNonNull(itinerary, "Itinerary is required for assignment");
        this.delivery = delivery.updateOnRouting(routeSpecification, this.itinerary);
    }

    public void deriveDeliveryProgress(HandlingHistory handlingHistory) {
        this.delivery = Delivery.derivedFrom(routeSpecification, getItinerary(), handlingHistory);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Cargo other && trackingId.equals(other.trackingId);
    }

    @Override
    public int hashCode() {
        return trackingId.hashCode();
    }

    @Override
    public String toString() {
        return trackingId;
    }
}
