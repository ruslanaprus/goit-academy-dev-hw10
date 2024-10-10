package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TimeResponseBuilder;
import org.example.service.TimezoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TimeServlet.class);

    private final TimezoneService timezoneService = new TimezoneService();
    private final TimeResponseBuilder timeResponseBuilder = new TimeResponseBuilder();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezoneParam = req.getParameter("timezone");
        ZoneId zoneId;

        try {
            zoneId = timezoneService.getZoneId(timezoneParam);
        } catch (DateTimeException e) {
            logger.error("Invalid timezone format: {}", timezoneParam, e);
            resp.setContentType("text/html");
            resp.getWriter().write("<html><body><h1>Invalid timezone format! Received timezone parameter: "
                    + timezoneParam + "</h1></body></html>");
            return;
        }

        String responseHtml = timeResponseBuilder.buildResponse(zoneId);

        logger.info("Generated HTML response: {}", responseHtml);

        resp.setContentType("text/html");
        resp.getWriter().write(responseHtml);
        resp.getWriter().close();

        logger.info("TimeServlet request completed successfully.");
    }
}