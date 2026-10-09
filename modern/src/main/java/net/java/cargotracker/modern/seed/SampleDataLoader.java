package net.java.cargotracker.modern.seed;

import static net.java.cargotracker.modern.seed.DateUtil.toDate;

import java.time.Instant;
import java.util.List;
import net.java.cargotracker.modern.domain.Cargo;
import net.java.cargotracker.modern.domain.HandlingEvent;
import net.java.cargotracker.modern.domain.HandlingEvent.Type;
import net.java.cargotracker.modern.domain.Itinerary;
import net.java.cargotracker.modern.domain.Leg;
import net.java.cargotracker.modern.domain.Location;
import net.java.cargotracker.modern.domain.RouteSpecification;
import net.java.cargotracker.modern.domain.Voyage;
import net.java.cargotracker.modern.repository.CargoRepository;
import net.java.cargotracker.modern.repository.HandlingEventRepository;
import net.java.cargotracker.modern.repository.LocationRepository;
import net.java.cargotracker.modern.repository.VoyageRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds the same raw inputs as legacy SampleDataGenerator. Delivery state is
 * derived from these inputs; no output value is stored here.
 */
@Component
public class SampleDataLoader implements ApplicationRunner {

    static final Location HONGKONG = new Location("CNHKG", "Hong Kong");
    static final Location MELBOURNE = new Location("AUMEL", "Melbourne");
    static final Location STOCKHOLM = new Location("SESTO", "Stockholm");
    static final Location HELSINKI = new Location("FIHEL", "Helsinki");
    static final Location CHICAGO = new Location("USCHI", "Chicago");
    static final Location TOKYO = new Location("JNTKO", "Tokyo");
    static final Location HAMBURG = new Location("DEHAM", "Hamburg");
    static final Location SHANGHAI = new Location("CNSHA", "Shanghai");
    static final Location ROTTERDAM = new Location("NLRTM", "Rotterdam");
    static final Location GOTHENBURG = new Location("SEGOT", "Guttenburg");
    static final Location HANGZOU = new Location("CNHGH", "Hangzhou");
    static final Location NEWYORK = new Location("USNYC", "New York");
    static final Location DALLAS = new Location("USDAL", "Dallas");

    static final Voyage HONGKONG_TO_NEW_YORK = new Voyage("0100S");
    static final Voyage NEW_YORK_TO_DALLAS = new Voyage("0200T");
    static final Voyage DALLAS_TO_HELSINKI = new Voyage("0300A");
    static final Voyage HELSINKI_TO_HONGKONG = new Voyage("0400S");
    static final Voyage DALLAS_TO_HELSINKI_ALT = new Voyage("0301S");

    private final CargoRepository cargoRepository;
    private final HandlingEventRepository handlingEventRepository;
    private final LocationRepository locationRepository;
    private final VoyageRepository voyageRepository;

    public SampleDataLoader(CargoRepository cargoRepository, HandlingEventRepository handlingEventRepository,
            LocationRepository locationRepository, VoyageRepository voyageRepository) {
        this.cargoRepository = cargoRepository;
        this.handlingEventRepository = handlingEventRepository;
        this.locationRepository = locationRepository;
        this.voyageRepository = voyageRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        loadSampleData();
    }

    public void loadSampleData() {
        List.of(HONGKONG, MELBOURNE, STOCKHOLM, HELSINKI, CHICAGO, TOKYO, HAMBURG, SHANGHAI,
                ROTTERDAM, GOTHENBURG, HANGZOU, NEWYORK, DALLAS).forEach(locationRepository::store);
        List.of(HONGKONG_TO_NEW_YORK, NEW_YORK_TO_DALLAS, DALLAS_TO_HELSINKI, HELSINKI_TO_HONGKONG,
                DALLAS_TO_HELSINKI_ALT).forEach(voyageRepository::store);
        loadSampleCargos();
    }

    private void loadSampleCargos() {
        Cargo abc123 = new Cargo("ABC123", new RouteSpecification(HONGKONG, HELSINKI, toDate("2016-03-15")));
        abc123.assignToRoute(new Itinerary(List.of(
                new Leg(HONGKONG_TO_NEW_YORK, HONGKONG, NEWYORK, toDate("2016-03-02"), toDate("2016-03-05")),
                new Leg(NEW_YORK_TO_DALLAS, NEWYORK, DALLAS, toDate("2016-03-06"), toDate("2016-03-08")),
                new Leg(DALLAS_TO_HELSINKI, DALLAS, HELSINKI, toDate("2016-03-09"), toDate("2016-03-12")))));
        cargoRepository.store(abc123);
        register("2016-03-01", "ABC123", null, "CNHKG", Type.RECEIVE);
        register("2016-03-02", "ABC123", "0100S", "CNHKG", Type.LOAD);
        register("2016-03-05", "ABC123", "0100S", "USNYC", Type.UNLOAD);
        abc123.deriveDeliveryProgress(handlingEventRepository.lookupHandlingHistoryOfCargo("ABC123"));

        Cargo jkl567 = new Cargo("JKL567", new RouteSpecification(HANGZOU, STOCKHOLM, toDate("2016-03-18")));
        jkl567.assignToRoute(new Itinerary(List.of(
                new Leg(HONGKONG_TO_NEW_YORK, HANGZOU, NEWYORK, toDate("2016-03-03"), toDate("2016-03-05")),
                new Leg(NEW_YORK_TO_DALLAS, NEWYORK, DALLAS, toDate("2016-03-06"), toDate("2016-03-08")),
                new Leg(DALLAS_TO_HELSINKI, DALLAS, STOCKHOLM, toDate("2016-03-09"), toDate("2016-03-11")))));
        cargoRepository.store(jkl567);
        register("2016-03-01", "JKL567", null, "CNHGH", Type.RECEIVE);
        register("2016-03-03", "JKL567", "0100S", "CNHGH", Type.LOAD);
        register("2016-03-05", "JKL567", "0100S", "USNYC", Type.UNLOAD);
        register("2016-03-06", "JKL567", "0100S", "USNYC", Type.LOAD);
        jkl567.deriveDeliveryProgress(handlingEventRepository.lookupHandlingHistoryOfCargo("JKL567"));

        Cargo def789 = new Cargo("DEF789", new RouteSpecification(HONGKONG, MELBOURNE, toDate("2016-11-18")));
        cargoRepository.store(def789);

        Cargo mno456 = new Cargo("MNO456", new RouteSpecification(NEWYORK, DALLAS, toDate("2016-3-27")));
        mno456.assignToRoute(new Itinerary(List.of(
                new Leg(NEW_YORK_TO_DALLAS, NEWYORK, DALLAS, toDate("2016-10-24"), toDate("2016-10-25")))));
        cargoRepository.store(mno456);
        register("2016-10-18", "MNO456", null, "USNYC", Type.RECEIVE);
        register("2016-10-24", "MNO456", "0200T", "USNYC", Type.LOAD);
        register("2016-10-25", "MNO456", "0200T", "USDAL", Type.UNLOAD);
        register("2016-10-26", "MNO456", null, "USDAL", Type.CUSTOMS);
        register("2016-10-27", "MNO456", null, "USDAL", Type.CLAIM);
        mno456.deriveDeliveryProgress(handlingEventRepository.lookupHandlingHistoryOfCargo("MNO456"));
    }

    /** Same lookups and checks as legacy HandlingEventFactory.createHandlingEvent. */
    private void register(String completionDate, String trackingId, String voyageNumber, String unLocode, Type type) {
        if (cargoRepository.find(trackingId) == null) {
            throw new IllegalStateException("No cargo with tracking id " + trackingId + " exists in the system");
        }
        Voyage voyage = null;
        if (voyageNumber != null) {
            voyage = voyageRepository.find(voyageNumber);
            if (voyage == null) {
                throw new IllegalStateException("No voyage with number " + voyageNumber + " exists in the system");
            }
        }
        Location location = locationRepository.find(unLocode);
        if (location == null) {
            throw new IllegalStateException("No location with UN locode " + unLocode + " exists in the system");
        }
        handlingEventRepository.store(new HandlingEvent(trackingId, toDate(completionDate), Instant.now(),
                type, location, voyage));
    }
}
