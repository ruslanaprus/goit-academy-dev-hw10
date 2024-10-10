package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimezoneService {
    private static final Logger logger = LoggerFactory.getLogger(TimezoneService.class);

    public ZoneId getZoneId(String timezoneParam) {
        if (timezoneParam == null || timezoneParam.isEmpty()) {
            return ZoneId.of("UTC");
        }

        if (timezoneParam.startsWith("UTC")) {
            try {
                String offsetStr = timezoneParam.substring(3).trim();

                if (!offsetStr.startsWith("+") && !offsetStr.startsWith("-")) {
                    offsetStr = "+" + offsetStr;
                }

                ZoneOffset offset = ZoneOffset.of(offsetStr);
                return ZoneId.ofOffset("UTC", offset);
            } catch (Exception e) {
                logger.error("Invalid offset format for timezone: {}", timezoneParam, e);
                throw new IllegalArgumentException("Invalid timezone offset");
            }
        }

        return ZoneId.of(timezoneParam);
    }
}