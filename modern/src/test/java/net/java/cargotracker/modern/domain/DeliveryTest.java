package net.java.cargotracker.modern.domain;

import static net.java.cargotracker.modern.seed.DateUtil.toDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class DeliveryTest {

    private static final Location A = new Location("AAAAA", "A");
    private static final Location B = new Location("BBBBB", "B");
    private static final Location C = new Location("CCCCC", "C");
    private static final Voyage V1 = new Voyage("V1");
    private static final Voyage V2 = new Voyage("V2");

    private static Cargo routedCargo(String deadline) {
        Cargo cargo = new Cargo("T1", new RouteSpecification(A, C, toDate(deadline)));
        cargo.assignToRoute(new Itinerary(List.of(
                new Leg(V1, A, B, toDate("2020-01-02"), toDate("2020-01-03")),
                new Leg(V2, B, C, toDate("2020-01-04"), toDate("2020-01-05")))));
        return cargo;
    }

    private static HandlingEvent event(String date, HandlingEvent.Type type, Location at, Voyage voyage) {
        return new HandlingEvent("T1", toDate(date), Instant.EPOCH, type, at, voyage);
    }

    @Test
    void newCargoIsNotRoutedAndNotReceived() {
        Cargo cargo = new Cargo("T1", new RouteSpecification(A, C, toDate("2020-02-01")));
        assertEquals(RoutingStatus.NOT_ROUTED, cargo.getDelivery().getRoutingStatus());
        assertEquals(TransportStatus.NOT_RECEIVED, cargo.getDelivery().getTransportStatus());
        assertEquals(Location.UNKNOWN, cargo.getDelivery().getLastKnownLocation());
        assertNull(cargo.getDelivery().getEstimatedTimeOfArrival());
    }

    @Test
    void onTrackCargoHasEtaOfFinalLeg() {
        Cargo cargo = routedCargo("2020-02-01");
        cargo.deriveDeliveryProgress(new HandlingHistory(List.of(
                event("2020-01-01", HandlingEvent.Type.RECEIVE, A, null),
                event("2020-01-03", HandlingEvent.Type.UNLOAD, B, V1))));
        Delivery delivery = cargo.getDelivery();
        assertEquals(RoutingStatus.ROUTED, delivery.getRoutingStatus());
        assertEquals(TransportStatus.IN_PORT, delivery.getTransportStatus());
        assertEquals(toDate("2020-01-05"), delivery.getEstimatedTimeOfArrival());
        assertEquals(new HandlingActivity(HandlingEvent.Type.LOAD, B, V2), delivery.getNextExpectedActivity());
    }

    @Test
    void loadOnWrongVoyageIsMisdirectedAndHasNoEta() {
        Cargo cargo = routedCargo("2020-02-01");
        cargo.deriveDeliveryProgress(new HandlingHistory(List.of(
                event("2020-01-04", HandlingEvent.Type.LOAD, B, V1))));
        Delivery delivery = cargo.getDelivery();
        assertTrue(delivery.isMisdirected());
        assertEquals(TransportStatus.ONBOARD_CARRIER, delivery.getTransportStatus());
        assertEquals(V1, delivery.getCurrentVoyage());
        assertNull(delivery.getEstimatedTimeOfArrival());
        assertTrue(delivery.getNextExpectedActivity().isEmpty());
    }

    @Test
    void deadlineBeforeFinalArrivalIsMisrouted() {
        Cargo cargo = routedCargo("2020-01-04");
        assertEquals(RoutingStatus.MISROUTED, cargo.getDelivery().getRoutingStatus());
    }

    @Test
    void unloadAtDestinationSetsAtDestination() {
        Cargo cargo = routedCargo("2020-02-01");
        cargo.deriveDeliveryProgress(new HandlingHistory(List.of(
                event("2020-01-05", HandlingEvent.Type.UNLOAD, C, V2))));
        assertTrue(cargo.getDelivery().isUnloadedAtDestination());
        assertFalse(cargo.getDelivery().isMisdirected());
    }

    @Test
    void duplicateRegistrationsCountOnce() {
        HandlingEvent first = event("2020-01-01", HandlingEvent.Type.RECEIVE, A, null);
        HandlingEvent duplicate = new HandlingEvent("T1", toDate("2020-01-01"), Instant.now(),
                HandlingEvent.Type.RECEIVE, A, null);
        assertEquals(1, new HandlingHistory(List.of(first, duplicate)).getDistinctEventsByCompletionTime().size());
    }

    @Test
    void lenientDateParseMatchesLegacySampleData() {
        assertEquals(toDate("2016-03-27"), toDate("2016-3-27"));
    }
}
