package net.java.cargotracker.modern.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class Itinerary {

    public static final Itinerary EMPTY_ITINERARY = new Itinerary();
    private static final Instant END_OF_DAYS = Instant.ofEpochMilli(Long.MAX_VALUE);

    private final List<Leg> legs;

    private Itinerary() {
        this.legs = List.of();
    }

    public Itinerary(List<Leg> legs) {
        Objects.requireNonNull(legs);
        if (legs.isEmpty()) {
            throw new IllegalArgumentException("The validated collection is empty");
        }
        this.legs = List.copyOf(legs);
    }

    public List<Leg> getLegs() {
        return legs;
    }

    /** Test if the given handling event is expected when executing this itinerary. */
    public boolean isExpected(HandlingEvent event) {
        if (legs.isEmpty()) {
            return true;
        }
        switch (event.getType()) {
            case RECEIVE:
                return legs.get(0).loadLocation().equals(event.getLocation());
            case LOAD:
                for (Leg leg : legs) {
                    if (leg.loadLocation().sameIdentityAs(event.getLocation())
                            && leg.voyage().sameIdentityAs(event.getVoyage())) {
                        return true;
                    }
                }
                return false;
            case UNLOAD:
                for (Leg leg : legs) {
                    if (leg.unloadLocation().equals(event.getLocation())
                            && leg.voyage().equals(event.getVoyage())) {
                        return true;
                    }
                }
                return false;
            case CLAIM:
                return getLastLeg().unloadLocation().equals(event.getLocation());
            default:
                return true;
        }
    }

    Location getInitialDepartureLocation() {
        return legs.isEmpty() ? Location.UNKNOWN : legs.get(0).loadLocation();
    }

    Location getFinalArrivalLocation() {
        return legs.isEmpty() ? Location.UNKNOWN : getLastLeg().unloadLocation();
    }

    Instant getFinalArrivalDate() {
        Leg lastLeg = getLastLeg();
        return lastLeg == null ? END_OF_DAYS : lastLeg.unloadTime();
    }

    Leg getLastLeg() {
        return legs.isEmpty() ? null : legs.get(legs.size() - 1);
    }
}
