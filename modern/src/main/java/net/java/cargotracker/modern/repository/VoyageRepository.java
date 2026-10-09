package net.java.cargotracker.modern.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import net.java.cargotracker.modern.domain.Voyage;
import org.springframework.stereotype.Repository;

@Repository
public class VoyageRepository {

    private final Map<String, Voyage> voyages = new LinkedHashMap<>();

    public synchronized Voyage find(String voyageNumber) {
        return voyages.get(voyageNumber);
    }

    public synchronized void store(Voyage voyage) {
        voyages.put(voyage.getVoyageNumber(), voyage);
    }
}
