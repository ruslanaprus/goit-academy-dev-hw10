package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeResponseBuilder {
    private static final Logger logger = LoggerFactory.getLogger(TimeResponseBuilder.class);

    public String buildResponse(ZoneId zoneId) {
        OffsetDateTime localTimeWithOffset = OffsetDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = localTimeWithOffset.format(formatter);

        ZoneOffset offset = localTimeWithOffset.getOffset();
        String formattedTimeWithZone = formattedTime + " UTC" + offset.getId();
        logger.info("Formatted time: {}", formattedTimeWithZone);

        return generateHtmlResponse(formattedTimeWithZone, zoneId);
    }

    private String generateHtmlResponse(String formattedTime, ZoneId zoneId) {
        StringBuilder responseHtml = new StringBuilder();
        responseHtml.append("<html><head><title>Current Time</title></head><body>")
                .append("<h1>Current Time in ").append(zoneId.getId()).append("</h1>")
                .append("<p>The current time is: <strong>")
                .append(formattedTime)
                .append("</strong></p>")
                .append("</body></html>");

        return responseHtml.toString();
    }
}
