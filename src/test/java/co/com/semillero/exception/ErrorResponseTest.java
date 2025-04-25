package co.com.semillero.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayName("ErrorResponse")
class ErrorResponseTest {

    @Test
    @DisplayName("Should correctly initialize ErrorResponse with exception message and stack trace")
    void shouldInitializeErrorResponseWithExceptionDetails() {
        Exception exception = new Exception("Test exception");

        ErrorResponse errorResponse = new ErrorResponse(exception);

        assertEquals("Test exception", errorResponse.getMessage());
        assertNotNull(errorResponse.getStackTraceAsString());
        assertTrue(errorResponse.getStackTraceAsString().contains("Test exception"));
    }

    @Test
    @DisplayName("Should allow setting and getting custom message")
    void shouldAllowSettingAndGettingCustomMessage() {
        ErrorResponse errorResponse = new ErrorResponse(new Exception("Initial message"));

        errorResponse.setMessage("Custom message");

        assertEquals("Custom message", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should allow setting and getting custom stack trace")
    void shouldAllowSettingAndGettingCustomStackTrace() {
        ErrorResponse errorResponse = new ErrorResponse(new Exception("Initial message"));

        errorResponse.setStackTrace("Custom stack trace");

        assertEquals("Custom stack trace", errorResponse.getStackTraceAsString());
    }

    @Test
    @DisplayName("Should handle null exception gracefully")
    void shouldHandleNullExceptionGracefully() {
        ErrorResponse errorResponse = new ErrorResponse(null);

        assertNull(errorResponse.getMessage());
        assertNotNull(errorResponse.getStackTraceAsString());
    }
}
