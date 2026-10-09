package net.java.cargotracker.modern.domain;

import java.time.Instant;
import java.util.Objects;

/**
 * Equality matches legacy HandlingEvent.sameEventAs: cargo, voyage,
 * completion time, location, and type. Registration time is not part of it.
 */
public final class HandlingEvent {

    public enum Type {
        LOAD(true), UNLOAD(true), RECEIVE(false), CLAIM(false), CUSTOMS(false);

        private final boolean voyageRequired;

        Type(boolean voyageRequired) {
            this.voyageRequired = voyageRequired;
        }

        public boolean requiresVoyage() {
            return voyageRequired;
        }

        public boolean prohibitsVoyage() {
            return !voyageRequired;
        }
    }

    private final String trackingId;
    private final Type type;
    private final Location location;
    private final Voyage voyage;
    private final Instant completionTime;
    private final Instant registrationTime;

    public HandlingEvent(String trackingId, Instant completionTime, Instant registrationTime,
            Type type, Location location, Voyage voyage) {
        this.trackingId = Objects.requireNonNull(trackingId, "Cargo is required");
        this.completionTime = Objects.requireNonNull(completionTime, "Completion time is required");
        this.registrationTime = Objects.requireNonNull(registrationTime, "Registration time is required");
        this.type = Objects.requireNonNull(type, "Handling event type is required");
        this.location = Objects.requireNonNull(location, "Location is required");
        if (voyage == null && type.requiresVoyage()) {
            throw new IllegalArgumentException("Voyage is required for event type " + type);
        }
        if (voyage != null && type.prohibitsVoyage()) {
            throw new IllegalArgumentException("Voyage is not allowed with event type " + type);
        }
        this.voyage = voyage;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Type getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public Voyage getVoyage() {
        return voyage == null ? Voyage.NONE : voyage;
    }

    public Instant getCompletionTime() {
        return completionTime;
    }

    public Instant getRegistrationTime() {
        return registrationTime;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof HandlingEvent other
                && trackingId.equals(other.trackingId)
                && Objects.equals(voyage, other.voyage)
                && completionTime.equals(other.completionTime)
                && location.equals(other.location)
                && type == other.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingId, voyage, completionTime, location, type);
    }
}
