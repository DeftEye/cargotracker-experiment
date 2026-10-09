package net.java.cargotracker.modern.seed;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Legacy parses sample dates in the JVM default time zone with a lenient
 * SimpleDateFormat, so "2016-3-27" is valid. Keep both behaviours.
 */
public final class DateUtil {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-M-d");

    private DateUtil() {
    }

    public static Instant toDate(String date) {
        return toDate(date, "00:00");
    }

    public static Instant toDate(String date, String time) {
        return LocalDate.parse(date, DATE).atTime(LocalTime.parse(time))
                .atZone(ZoneId.systemDefault()).toInstant();
    }
}
