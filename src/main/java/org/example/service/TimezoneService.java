package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimezoneService {
    private static final Logger logger = LoggerFactory.getLogger(TimezoneService.class);

    public ZoneId getZoneId(String timezoneParam) {
        logger.info("Received timezone parameter: {}", timezoneParam);

        if (timezoneParam == null || timezoneParam.isEmpty()) {
            logger.info("No timezone provided, using UTC as default");
            return ZoneId.of("UTC");
        }

        // handle timezone with offset like UTC+2 or UTC-05:00
        if (timezoneParam.startsWith("UTC")) {
            try {
                // extract the offset part (like +2 or -05:00), and remove any extra spaces
                String offsetStr = timezoneParam.substring(3).trim();  // skip "UTC" and trim spaces

                // check if offset starts with '+' or '-'
                if (!offsetStr.startsWith("+") && !offsetStr.startsWith("-")) {
                    offsetStr = "+" + offsetStr;
                }

                // create a ZoneOffset and return the ZoneId using that offset
                ZoneOffset offset = ZoneOffset.of(offsetStr);
                return ZoneId.ofOffset("UTC", offset);
            } catch (Exception e) {
                logger.error("Invalid offset format for timezone: {}", timezoneParam, e);
                throw new IllegalArgumentException("Invalid timezone offset");
            }
        }

        // for other valid zone IDs (like Europe/Berlin)
        return ZoneId.of(timezoneParam);
    }
}