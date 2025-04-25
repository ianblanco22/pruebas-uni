package co.com.semillero.util;

import co.com.semillero.constants.MessagesEnum;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BuildResponseUtilTest {

    @Test
    @DisplayName("BuildsSuccessResponseWithValidObject")
    void buildsSuccessResponseWithValidObject() {
        Object response = Map.of("key", "value");

        APIGatewayProxyResponseEvent result = BuildResponseUtil.buildSuccess(response);

        assertNotNull(result);
        assertEquals(200, result.getStatusCode());
        assertTrue(result.getBody().contains("\"key\":\"value\""));
        assertTrue(result.getHeaders().containsKey("Content-Type"));
    }

    @Test
    @DisplayName("BuildsErrorResponseWithDefaultValues")
    void buildsErrorResponseWithDefaultValues() throws IOException {
        APIGatewayProxyResponseEvent result = BuildResponseUtil.buildErrorDefault();

        assertNotNull(result);
        assertEquals(500, result.getStatusCode());
        assertTrue(result.getBody().contains(MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage()));
        assertTrue(result.getHeaders().containsKey("Access-Control-Allow-Origin"));
    }
}