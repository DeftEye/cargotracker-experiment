package net.java.cargotracker.modern.domain;

import static net.java.cargotracker.modern.domain.RoutingStatus.MISROUTED;
import static net.java.cargotracker.modern.domain.RoutingStatus.NOT_ROUTED;
import static net.java.cargotracker.modern.domain.RoutingStatus.ROUTED;

import java.time.Instant;
import java.util.Iterator;
import java.util.Objects;

/**
 * The actual transportation of the cargo, as opposed to the customer
 * requirement (RouteSpecification) and the plan (Itinerary). Immutable
 * snapshot, recalculated when routing or handling changes.
 */
public final class Delivery {

    private final HandlingEvent lastEvent;
    private final boolean misdirected;
    private final RoutingStatus routingStatus;
    private final TransportStatus transportStatus;
    private final Location lastKnownLocation;
    private final Voyage currentVoyage;
    private final Instant eta;
    private final HandlingActivity nextExpectedActivity;
    private final boolean unloadedAtDestination;
    private final Instant calculatedAt;

    private Delivery(HandlingEvent lastEvent, Itinerary itinerary, RouteSpecification routeSpecification) {
        this.calculatedAt = Instant.now();
        this.lastEvent = lastEvent;
        this.misdirected = calculateMisdirectionStatus(itinerary);
        this.routingStatus = calculateRoutingStatus(itinerary, routeSpecification);
        this.transportStatus = calculateTransportStatus();
        this.lastKnownLocation = lastEvent != null ? lastEvent.getLocation() : null;
        this.currentVoyage = transportStatus == TransportStatus.ONBOARD_CARRIER && lastEvent != null
                ? lastEvent.getVoyage() : null;
        this.eta = onTrack() ? itinerary.getFinalArrivalDate() : null;
        this.nextExpectedActivity = calculateNextExpectedActivity(routeSpecification, itinerary);
        this.unloadedAtDestination = lastEvent != null
                && lastEvent.getType() == HandlingEvent.Type.UNLOAD
                && routeSpecification.destination().sameIdentityAs(lastEvent.getLocation());
    }

    static Delivery derivedFrom(RouteSpecification routeSpecification, Itinerary itinerary,
            HandlingHistory handlingHistory) {
        Objects.requireNonNull(routeSpecification, "Route specification is required");
        Objects.requireNonNull(handlingHistory, "Delivery history is required");
        return new Delivery(handlingHistory.getMostRecentlyCompletedEvent(), itinerary, routeSpecification);
    }

    Delivery updateOnRouting(RouteSpecification routeSpecification, Itinerary itinerary) {
        Objects.requireNonNull(routeSpecification, "Route specification is required");
        return new Delivery(lastEvent, itinerary, routeSpecification);
    }

    public TransportStatus getTransportStatus() {
        return transportStatus;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation == null ? Location.UNKNOWN : lastKnownLocation;
    }

    public Voyage getCurrentVoyage() {
        return currentVoyage == null ? Voyage.NONE : currentVoyage;
    }

    public boolean isMisdirected() {
        return misdirected;
    }

    /** Null when the ETA is unknown. */
    public Instant getEstimatedTimeOfArrival() {
        return eta;
    }

    public HandlingActivity getNextExpectedActivity() {
        return nextExpectedActivity;
    }

    public boolean isUnloadedAtDestination() {
        return unloadedAtDestination;
    }

    public RoutingStatus getRoutingStatus() {
        return routingStatus;
    }

    public Instant getCalculatedAt() {
        return calculatedAt;
    }

    private TransportStatus calculateTransportStatus() {
        if (lastEvent == null) {
            return TransportStatus.NOT_RECEIVED;
        }
        return switch (lastEvent.getType()) {
            case LOAD -> TransportStatus.ONBOARD_CARRIER;
            case UNLOAD, RECEIVE, CUSTOMS -> TransportStatus.IN_PORT;
            case CLAIM -> TransportStatus.CLAIMED;
        };
    }

    private boolean calculateMisdirectionStatus(Itinerary itinerary) {
        return lastEvent != null && !itinerary.isExpected(lastEvent);
    }

    private RoutingStatus calculateRoutingStatus(Itinerary itinerary, RouteSpecification routeSpecification) {
        if (itinerary == null || itinerary == Itinerary.EMPTY_ITINERARY) {
            return NOT_ROUTED;
        }
        return routeSpecification.isSatisfiedBy(itinerary) ? ROUTED : MISROUTED;
    }

    private HandlingActivity calculateNextExpectedActivity(RouteSpecification routeSpecification,
            Itinerary itinerary) {
        if (!onTrack()) {
            return HandlingActivity.NO_ACTIVITY;
        }
        if (lastEvent == null) {
            return new HandlingActivity(HandlingEvent.Type.RECEIVE, routeSpecification.origin());
        }
        switch (lastEvent.getType()) {
            case LOAD:
                for (Leg leg : itinerary.getLegs()) {
                    if (leg.loadLocation().sameIdentityAs(lastEvent.getLocation())) {
                        return new HandlingActivity(HandlingEvent.Type.UNLOAD, leg.unloadLocation(), leg.voyage());
                    }
                }
                return HandlingActivity.NO_ACTIVITY;
            case UNLOAD:
                for (Iterator<Leg> it = itinerary.getLegs().iterator(); it.hasNext();) {
                    Leg leg = it.next();
                    if (leg.unloadLocation().sameIdentityAs(lastEvent.getLocation())) {
                        if (it.hasNext()) {
                            Leg nextLeg = it.next();
                            return new HandlingActivity(HandlingEvent.Type.LOAD, nextLeg.loadLocation(), nextLeg.voyage());
                        }
                        return new HandlingActivity(HandlingEvent.Type.CLAIM, leg.unloadLocation());
                    }
                }
                return HandlingActivity.NO_ACTIVITY;
            case RECEIVE:
                Leg firstLeg = itinerary.getLegs().get(0);
                return new HandlingActivity(HandlingEvent.Type.LOAD, firstLeg.loadLocation(), firstLeg.voyage());
            default:
                return HandlingActivity.NO_ACTIVITY;
        }
    }

    private boolean onTrack() {
        return routingStatus == ROUTED && !misdirected;
    }
}
