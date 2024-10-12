package org.example.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ErrorResponseUtilTest {
    private HttpServletResponse mockResponse;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        mockResponse = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(printWriter);
    }

    @Test
    void testSendRequest_SetsStatusAndContentType() throws IOException {
        String errorMessage = "Invalid inout!";

        ErrorResponseUtil.sendBadRequest(mockResponse, errorMessage);

        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(mockResponse).setContentType("text/html");

        printWriter.flush();

        String expectedHtml = "<html><body><h1>" + StringEscapeUtils.escapeHtml4(errorMessage) + "</h1></body></html>";
        assertEquals(expectedHtml, stringWriter.toString());
    }
}