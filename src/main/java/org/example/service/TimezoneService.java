package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimezoneService {
    private static final Logger logger = LoggerFactory.getLogger(TimezoneService.class);
    private static final String DEFAULT_TIMEZONE = "UTC";
    private static final String UTC_PREFIX = "UTC";

    public ZoneId getZoneId(String timezoneParam) {
        if (timezoneParam == null || timezoneParam.isEmpty()) {
            return ZoneId.of(DEFAULT_TIMEZONE);
        }

        if (timezoneParam.startsWith(UTC_PREFIX)) {
            try {
                String offsetStr = timezoneParam.substring(3).trim();

                if (!offsetStr.startsWith("+") && !offsetStr.startsWith("-")) {
                    offsetStr = "+" + offsetStr;
                }

                ZoneOffset offset = ZoneOffset.of(offsetStr);
                return ZoneId.ofOffset(UTC_PREFIX, offset);
            } catch (Exception e) {
                logger.error("Invalid offset format for timezone: {}", timezoneParam, e);
                throw new IllegalArgumentException("Invalid timezone offset");
            }
        }

        return ZoneId.of(timezoneParam);
    }
}