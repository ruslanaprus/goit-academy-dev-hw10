package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class TimezoneServiceTest {

    private TimezoneService timezoneService;

    @BeforeEach
    public void setUp() {
        timezoneService = new TimezoneService();
    }

    @Test
    void testGetZoneIdWithValidTimezone() {
        ZoneId zoneId = timezoneService.getZoneId("America/New_York");
        assertEquals(ZoneId.of("America/New_York"), zoneId);
    }

    @Test
    void testGetZoneIdWithUTCOffset() {
        ZoneId zoneId = timezoneService.getZoneId("UTC+5");
        assertEquals(ZoneId.ofOffset("UTC", ZoneOffset.of("+05:00")), zoneId);
    }

    @Test
    void testGetZoneIdWithInvalidTimezone() {
        assertThrows(DateTimeException.class, () -> {
            timezoneService.getZoneId("Invalid/Timezone");
        });
    }
}
