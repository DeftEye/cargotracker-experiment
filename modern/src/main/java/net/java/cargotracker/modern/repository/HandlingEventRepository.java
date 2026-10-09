package net.java.cargotracker.modern.repository;

import java.util.ArrayList;
import java.util.List;
import net.java.cargotracker.modern.domain.HandlingEvent;
import net.java.cargotracker.modern.domain.HandlingHistory;
import org.springframework.stereotype.Repository;

@Repository
public class HandlingEventRepository {

    private final List<HandlingEvent> events = new ArrayList<>();

    public synchronized void store(HandlingEvent event) {
        events.add(event);
    }

    public synchronized HandlingHistory lookupHandlingHistoryOfCargo(String trackingId) {
        return new HandlingHistory(events.stream().filter(e -> e.getTrackingId().equals(trackingId)).toList());
    }
}
