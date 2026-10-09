package net.java.cargotracker.modern.domain;

public record HandlingActivity(HandlingEvent.Type type, Location location, Voyage voyage) {

    public static final HandlingActivity NO_ACTIVITY = new HandlingActivity(null, null, null);

    public HandlingActivity(HandlingEvent.Type type, Location location) {
        this(type, location, null);
    }

    public boolean isEmpty() {
        return type == null && location == null && voyage == null;
    }
}
