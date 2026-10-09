package net.java.cargotracker.modern.domain;

import java.util.Objects;

/** Identity is the UN/LOCODE only, as in legacy Location.equals. */
public final class Location {

    public static final Location UNKNOWN = new Location("XXXXX", "Unknown location");

    private final String unLocode;
    private final String name;

    public Location(String unLocode, String name) {
        this.unLocode = Objects.requireNonNull(unLocode);
        this.name = Objects.requireNonNull(name);
    }

    public String getUnLocode() {
        return unLocode;
    }

    public String getName() {
        return name;
    }

    public boolean sameIdentityAs(Location other) {
        return other != null && unLocode.equals(other.unLocode);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Location other && sameIdentityAs(other);
    }

    @Override
    public int hashCode() {
        return unLocode.hashCode();
    }

    @Override
    public String toString() {
        return name + " [" + unLocode + "]";
    }
}
