package net.java.cargotracker.modern.web;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import net.java.cargotracker.modern.domain.Cargo;
import net.java.cargotracker.modern.domain.Delivery;
import net.java.cargotracker.modern.domain.HandlingEvent;

/**
 * Same text rules as legacy CargoTrackingViewAdapter. Legacy formats with the
 * JVM default time zone and the US English AM/PM markers.
 */
public final class CargoTrackingView {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a z", Locale.US).withZone(ZoneId.systemDefault());

    private final Cargo cargo;
    private final List<EventView> events;

    public CargoTrackingView(Cargo cargo, List<HandlingEvent> handlingEvents) {
        this.cargo = cargo;
        this.events = handlingEvents.stream().map(EventView::new).toList();
    }

    public String getTrackingId() {
        return cargo.getTrackingId();
    }

    public String getDestination() {
        return cargo.getRouteSpecification().destination().getName();
    }

    public String getStatusText() {
        Delivery delivery = cargo.getDelivery();
        return switch (delivery.getTransportStatus()) {
            case IN_PORT -> "In port " + delivery.getLastKnownLocation().getName();
            case ONBOARD_CARRIER -> "Onboard voyage " + delivery.getCurrentVoyage().getVoyageNumber();
            case CLAIMED -> "Claimed";
            case NOT_RECEIVED -> "Not received";
            case UNKNOWN -> "Unknown";
        };
    }

    public boolean isMisdirected() {
        return cargo.getDelivery().isMisdirected();
    }

    public String getEta() {
        Instant eta = cargo.getDelivery().getEstimatedTimeOfArrival();
        return eta == null ? "?" : DATE_FORMAT.format(eta);
    }

    public List<EventView> getEvents() {
        return events;
    }

    public final class EventView {

        private final HandlingEvent event;

        EventView(HandlingEvent event) {
            this.event = event;
        }

        public boolean isExpected() {
            return cargo.getItinerary().isExpected(event);
        }

        public String getDescription() {
            String location = event.getLocation().getName();
            String time = DATE_FORMAT.format(event.getCompletionTime());
            String voyage = event.getVoyage().getVoyageNumber();
            return switch (event.getType()) {
                case LOAD -> "Loaded onto voyage " + voyage + " in " + location + ", at " + time + ".";
                case UNLOAD -> "Unloaded off voyage " + voyage + " in " + location + ", at " + time + ".";
                case RECEIVE -> "Received in " + location + ", at " + time + ".";
                case CLAIM -> "Claimed in " + location + ", at " + time + ".";
                case CUSTOMS -> "Cleared customs in " + location + ", at " + time + ".";
            };
        }
    }
}
