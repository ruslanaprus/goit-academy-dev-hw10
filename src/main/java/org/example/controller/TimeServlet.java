package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.example.service.TimeResponseBuilder;
import org.example.service.TimezoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.ZoneId;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TimeServlet.class);

    private TimezoneService timezoneService;
    private TimeResponseBuilder timeResponseBuilder;

    @Override
    public void init() throws ServletException {
        super.init();
        this.timezoneService = new TimezoneService();
        this.timeResponseBuilder = new TimeResponseBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezoneParam = escapeHtml(req.getParameter("timezone"));
        ZoneId zoneId;

        try {
            zoneId = timezoneService.getZoneId(timezoneParam);
        } catch (DateTimeException | IllegalArgumentException e) {
            logger.error("Invalid timezone format: {}", timezoneParam, e);
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("<html><body><h1>Invalid timezone format! Received timezone parameter: "
                        + escapeHtml(timezoneParam) + "</h1></body></html>");
            }
            return;
        }

        String responseHtml = timeResponseBuilder.buildResponse(zoneId);

        logger.info("Generated HTML response: {}", responseHtml);

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(responseHtml);
        }

        logger.info("TimeServlet request completed successfully.");
    }

    private String escapeHtml(String input) {
        return input == null ? "" : StringEscapeUtils.escapeHtml4(input);
    }
}