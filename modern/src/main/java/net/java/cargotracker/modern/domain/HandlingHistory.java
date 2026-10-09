package net.java.cargotracker.modern.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public final class HandlingHistory {

    public static final HandlingHistory EMPTY = new HandlingHistory(List.of());

    private final List<HandlingEvent> handlingEvents;

    public HandlingHistory(Collection<HandlingEvent> handlingEvents) {
        this.handlingEvents = new ArrayList<>(Objects.requireNonNull(handlingEvents, "Handling events are required"));
    }

    /** Distinct events (no duplicate registrations), ordered by completion time. */
    public List<HandlingEvent> getDistinctEventsByCompletionTime() {
        List<HandlingEvent> ordered = new ArrayList<>(new LinkedHashSet<>(handlingEvents));
        ordered.sort(Comparator.comparing(HandlingEvent::getCompletionTime));
        return List.copyOf(ordered);
    }

    public HandlingEvent getMostRecentlyCompletedEvent() {
        List<HandlingEvent> distinct = getDistinctEventsByCompletionTime();
        return distinct.isEmpty() ? null : distinct.get(distinct.size() - 1);
    }
}
