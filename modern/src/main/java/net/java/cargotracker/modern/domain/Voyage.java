package net.java.cargotracker.modern.domain;

import java.util.Objects;

/**
 * Identity is the voyage number. Carrier movement schedules are not used by
 * the delivery rules in this slice, so they are not ported.
 */
public final class Voyage {

    public static final Voyage NONE = new Voyage("");

    private final String voyageNumber;

    public Voyage(String voyageNumber) {
        this.voyageNumber = Objects.requireNonNull(voyageNumber);
    }

    public String getVoyageNumber() {
        return voyageNumber;
    }

    public boolean sameIdentityAs(Voyage other) {
        return other != null && voyageNumber.equals(other.voyageNumber);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Voyage other && sameIdentityAs(other);
    }

    @Override
    public int hashCode() {
        return voyageNumber.hashCode();
    }

    @Override
    public String toString() {
        return voyageNumber;
    }
}
