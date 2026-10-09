package net.java.cargotracker.modern.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.java.cargotracker.modern.domain.Cargo;
import net.java.cargotracker.modern.domain.Delivery;
import net.java.cargotracker.modern.repository.CargoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** cargo-monitor-001: same path, keys, and key order as legacy CargoMonitoringService. */
@RestController
public class CargoMonitoringController {

    private final CargoRepository cargoRepository;

    public CargoMonitoringController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping(path = "/rest/cargo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getAllCargo() {
        return cargoRepository.findAll().stream().map(CargoMonitoringController::toJson).toList();
    }

    private static Map<String, Object> toJson(Cargo cargo) {
        Delivery delivery = cargo.getDelivery();
        String lastKnown = delivery.getLastKnownLocation().getUnLocode();
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("trackingId", cargo.getTrackingId());
        json.put("routingStatus", delivery.getRoutingStatus().toString());
        json.put("misdirected", delivery.isMisdirected());
        json.put("transportStatus", delivery.getTransportStatus().toString());
        json.put("atDestination", delivery.isUnloadedAtDestination());
        json.put("origin", cargo.getOrigin().getUnLocode());
        json.put("lastKnownLocation", lastKnown.equals("XXXXX") ? "Unknown" : lastKnown);
        return json;
    }
}
