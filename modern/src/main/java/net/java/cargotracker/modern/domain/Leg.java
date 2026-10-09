package net.java.cargotracker.modern.domain;

import java.time.Instant;
import java.util.Objects;

public record Leg(Voyage voyage, Location loadLocation, Location unloadLocation,
        Instant loadTime, Instant unloadTime) {

    public Leg {
        Objects.requireNonNull(voyage);
        Objects.requireNonNull(loadLocation);
        Objects.requireNonNull(unloadLocation);
        Objects.requireNonNull(loadTime);
        Objects.requireNonNull(unloadTime);
    }
}
