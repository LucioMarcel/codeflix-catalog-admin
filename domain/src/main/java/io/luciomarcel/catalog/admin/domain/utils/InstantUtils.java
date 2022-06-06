package io.luciomarcel.catalog.admin.domain.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    private InstantUtils() {}

    // 9 Casadas Defimais - NANOSECONDS
    // 6 Casadas Defimais - MICROSECONDS
    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }
    
}
