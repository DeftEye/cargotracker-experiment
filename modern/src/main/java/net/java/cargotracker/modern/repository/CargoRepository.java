package net.java.cargotracker.modern.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.java.cargotracker.modern.domain.Cargo;
import org.springframework.stereotype.Repository;

/** Keeps insertion order, which is the order legacy findAll returns for the sample data. */
@Repository
public class CargoRepository {

    private final Map<String, Cargo> cargos = new LinkedHashMap<>();

    public synchronized Cargo find(String trackingId) {
        return cargos.get(trackingId);
    }

    public synchronized List<Cargo> findAll() {
        return new ArrayList<>(cargos.values());
    }

    public synchronized void store(Cargo cargo) {
        cargos.put(cargo.getTrackingId(), cargo);
    }
}
