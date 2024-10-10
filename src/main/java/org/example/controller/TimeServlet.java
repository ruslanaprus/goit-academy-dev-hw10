package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TimeServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        String currentDir = System.getProperty("user.dir");
        logger.info("Current working directory: {}", currentDir);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime utcTime = LocalDateTime.now(ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = utcTime.format(formatter) + " UTC";
        logger.info("Formatted time: {}", formattedTime);

        resp.setContentType("text/html");

        StringBuilder responseHtml = new StringBuilder();
        responseHtml.append("<html><head><title>Current Time</title></head><body>")
                .append("<h1>Current Time in UTC</h1>")
                .append("<p>The current time is: <strong>")
                .append(formattedTime)
                .append("</strong></p>")
                .append("</body></html>");

        logger.info("Generated HTML response: {}", responseHtml.toString());

        resp.getWriter().write(responseHtml.toString());
        resp.getWriter().close();

        logger.info("TimeServlet request completed successfully.");
    }
}