package co.com.semillero.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadersRqTest {

    @Test
    @DisplayName("Should set and get contentLength correctly")
    void shouldSetAndGetContentLengthCorrectly() {
        HeadersRq headersRq = new HeadersRq();
        headersRq.setContentLength("1024");

        assertEquals("1024", headersRq.getContentLength());
    }

    @Test
    @DisplayName("Should set and get contentType correctly")
    void shouldSetAndGetContentTypeCorrectly() {
        HeadersRq headersRq = new HeadersRq();
        headersRq.setContentType("application/json");

        assertEquals("application/json", headersRq.getContentType());
    }

    @Test
    @DisplayName("Should set and get accept correctly")
    void shouldSetAndGetAcceptCorrectly() {
        HeadersRq headersRq = new HeadersRq();
        headersRq.setAccept("application/xml");

        assertEquals("application/xml", headersRq.getAccept());
    }

    @Test
    @DisplayName("Should handle null values for all fields")
    void shouldHandleNullValuesForAllFields() {
        HeadersRq headersRq = new HeadersRq();
        headersRq.setContentLength(null);
        headersRq.setContentType(null);
        headersRq.setAccept(null);

        assertNull(headersRq.getContentLength());
        assertNull(headersRq.getContentType());
        assertNull(headersRq.getAccept());
    }
}