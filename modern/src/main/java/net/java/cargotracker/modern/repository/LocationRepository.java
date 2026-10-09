package net.java.cargotracker.modern.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import net.java.cargotracker.modern.domain.Location;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository {

    private final Map<String, Location> locations = new LinkedHashMap<>();

    public synchronized Location find(String unLocode) {
        return locations.get(unLocode);
    }

    public synchronized void store(Location location) {
        locations.put(location.getUnLocode(), location);
    }
}
