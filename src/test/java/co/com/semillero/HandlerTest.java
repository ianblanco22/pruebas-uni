package co.com.semillero;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.model.Client;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.util.BuildResponseUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HandlerTest {

    private Handler handler;

    @Mock
    private DynamoDbEnhancedClient dynamoClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = spy(new Handler());
        handler.dynamoClient = dynamoClient;
    }

    @Nested
    @DisplayName("execute method")
    class ExecuteMethod {

        @Test
        @DisplayName("returns success response for valid guardar request")
        void returnsSuccessResponseForGuardarRequest() throws IOException {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("servicio", "guardar");
            request.setHeaders(headers);
            request.setBody("{\"strFirstName\":\"John\"}");

            doReturn(BuildResponseUtil.buildSuccess("Client saved successfully"))
                    .when(handler).redirect(request);

            APIGatewayProxyResponseEvent response = handler.execute(request);

            assertEquals(200, response.getStatusCode());
            assertEquals("Client saved successfully", response.getBody());
        }

        @Test
        @DisplayName("returns error response for invalid servicio header")
        void returnsErrorResponseForInvalidServicioHeader() throws IOException {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("servicio", "invalid");
            request.setHeaders(headers);
            request.setBody("{\"strFirstName\":\"John\"}");

            doThrow(new RuntimeException("Servicio no disponible"))
                    .when(handler).redirect(request);

            APIGatewayProxyResponseEvent response = handler.execute(request);

            assertEquals(500, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("redirect method")
    class RedirectMethod {

        @Test
        @DisplayName("handles guardar service successfully")
        void handlesGuardarServiceSuccessfully() throws IOException, ErrorResponse {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("servicio", "guardar");
            request.setHeaders(headers);
            request.setBody("{\"strFirstName\":\"John\"}");

            Client client = new Client();
            client.setStrFirstName("John");

            doReturn("Client saved successfully")
                    .when(handler.service).saveClient(any(), eq(client), anyString());

            Object response = handler.redirect(request);

            assertEquals(BuildResponseUtil.buildSuccess("Client saved successfully"), response);
        }

        @Test
        @DisplayName("handles consultar service successfully")
        void handlesConsultarServiceSuccessfully() throws IOException {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("servicio", "consultar");
            request.setHeaders(headers);
            request.setBody("{\"strFirstName\":\"John\"}");

            Client client = new Client();
            client.setStrFirstName("John");

            ClientEntity entity = new ClientEntity();
            entity.setStrFirstName("John");

            doReturn(entity)
                    .when(handler.service).getClient(any(), eq(client), anyString());

            Object response = handler.redirect(request);

            assertEquals(BuildResponseUtil.buildSuccess(entity), response);
        }

        @Test
        @DisplayName("returns error response for unsupported service")
        void returnsErrorResponseForUnsupportedService() throws IOException {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("servicio", "unsupported");
            request.setHeaders(headers);
            request.setBody("{\"strFirstName\":\"John\"}");

            Object response = handler.redirect(request);

            assertEquals(BuildResponseUtil.buildErrorDefault(), response);
        }


    }
}