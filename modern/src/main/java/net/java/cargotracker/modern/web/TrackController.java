package net.java.cargotracker.modern.web;

import net.java.cargotracker.modern.domain.Cargo;
import net.java.cargotracker.modern.repository.CargoRepository;
import net.java.cargotracker.modern.repository.HandlingEventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * public-track-001: same URL and form field name as legacy public/track.xhtml.
 * Legacy queues a not-found message but its public template never shows it,
 * so a missing cargo renders the form with no result block and no message.
 */
@Controller
public class TrackController {

    static final String TRACKING_ID_FIELD = "trackingForm:trackingId_input";

    private final CargoRepository cargoRepository;
    private final HandlingEventRepository handlingEventRepository;

    public TrackController(CargoRepository cargoRepository, HandlingEventRepository handlingEventRepository) {
        this.cargoRepository = cargoRepository;
        this.handlingEventRepository = handlingEventRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/public/track.xhtml";
    }

    @GetMapping("/public/track.xhtml")
    public String form() {
        return "track";
    }

    @PostMapping("/public/track.xhtml")
    public String onTrackById(@RequestParam(name = TRACKING_ID_FIELD, required = false) String trackingId,
            Model model) {
        String id = trackingId == null ? null : trackingId.trim();
        model.addAttribute("trackingId", id);
        if (id == null || id.isEmpty()) {
            return "track";
        }
        Cargo cargo = cargoRepository.find(id);
        if (cargo != null) {
            model.addAttribute("cargo", new CargoTrackingView(cargo,
                    handlingEventRepository.lookupHandlingHistoryOfCargo(id).getDistinctEventsByCompletionTime()));
        }
        return "track";
    }
}
