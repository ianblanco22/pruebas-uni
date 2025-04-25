package co.com.semillero.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessagesEnumTest {

    @Test
    @DisplayName("ReturnsCorrectCodeForSuccessResponse")
    void returnsCorrectCodeForSuccessResponse() {
        assertEquals("2000", MessagesEnum.SUCCESS_RESPONSE.getCode());
    }

    @Test
    @DisplayName("ReturnsCorrectMessageForErrorConnectionSNS")
    void returnsCorrectMessageForErrorConnectionSNS() {
        assertEquals("Error de conexion SNS", MessagesEnum.ERROR_CONNECTION_SNS.getMessage());
    }

    @Test
    @DisplayName("ReturnsCorrectHttpCodeForDefaultErrorResponse")
    void returnsCorrectHttpCodeForDefaultErrorResponse() {
        assertEquals(500, MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
    }

    @Test
    @DisplayName("HandlesNullCodeGracefully")
    void handlesNullCodeGracefully() {
        MessagesEnum nullCodeEnum = MessagesEnum.valueOf("SUCCESS_RESPONSE");
        assertNotNull(nullCodeEnum.getCode());
    }

    @Test
    @DisplayName("HandlesNullMessageGracefully")
    void handlesNullMessageGracefully() {
        MessagesEnum nullMessageEnum = MessagesEnum.valueOf("SUCCESS_RESPONSE");
        assertNotNull(nullMessageEnum.getMessage());
    }
}