package org.example.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TimezoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.DateTimeException;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(TimezoneValidateFilter.class);

    private TimezoneService timezoneService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.timezoneService = new TimezoneService();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezoneParam = req.getParameter("timezone");
        logger.debug("Validating timezone parameter: {}", timezoneParam);

        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                timezoneService.getZoneId(timezoneParam);
            } catch (DateTimeException | IllegalArgumentException e) {
                logger.error("Invalid timezone parameter: {}", timezoneParam, e);
                sendBadResponse(res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private void sendBadResponse(HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.setContentType("text/html");
        String errorMessage = "<html><body><h1>Invalid timezone</h1></body></html>";
        res.getWriter().write(errorMessage);
        logger.debug("Sent 400 Bad Request response due to invalid timezone.");
    }
}
