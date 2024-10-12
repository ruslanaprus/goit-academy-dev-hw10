package org.example.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ErrorResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(ErrorResponseUtil.class);

    public static void sendBadRequest(HttpServletResponse res, String errorMessage) throws IOException {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.setContentType("text/html");
        String escapedMessage = StringEscapeUtils.escapeHtml4(errorMessage);
        String errorHtml = "<html><body><h1>" + escapedMessage + "</h1></body></html>";
        res.getWriter().write(errorHtml);
        logger.debug("Sent 400 Bad Request response with message: {}", escapedMessage);
    }
}