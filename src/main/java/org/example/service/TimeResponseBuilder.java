package org.example.service;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeResponseBuilder {
    private static final Logger logger = LoggerFactory.getLogger(TimeResponseBuilder.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String buildResponse(ZoneId zoneId) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        String formattedTime = zonedDateTime.format(FORMATTER);
        ZoneOffset offset = zonedDateTime.getOffset();
        String formattedTimeWithZone = formattedTime + " UTC" + offset.getId();

        logger.info("Formatted time: {}", formattedTimeWithZone);

        return generateHtmlResponse(formattedTimeWithZone, zoneId);
    }

    private String generateHtmlResponse(String formattedTime, ZoneId zoneId) {
        StringBuilder responseHtml = new StringBuilder();
        responseHtml.append("<html>")
                .append("<head><title>Current Time</title></head>")
                .append("<body>")
                .append("<h1>Current Time in ").append(escapeHtml(zoneId.getId())).append("</h1>")
                .append("<p>The current time is: <strong>").append(escapeHtml(formattedTime)).append("</strong></p>")
                .append("</body>")
                .append("</html>");
        return responseHtml.toString();
    }

    private String escapeHtml(String input) {
        return input == null ? "" : StringEscapeUtils.escapeHtml4(input);
    }
}