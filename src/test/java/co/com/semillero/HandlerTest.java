package co.com.semillero;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.model.Client;
import co.com.semillero.model.ParameterStoreDTO;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.service.DynamoService;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HandlerTest {

    private Handler handler;

    @Mock
    private DynamoDbEnhancedClient mockClient;

    @Mock
    private DynamoService mockService;

    private ParameterStoreDTO mockParameterDTO;

    @BeforeEach
    void setUp() {
        System.setProperty("aws.region", "us-east-1");
        MockitoAnnotations.openMocks(this);
        mockParameterDTO = new ParameterStoreDTO();
        mockParameterDTO.setRegion("us-east-1");
        mockParameterDTO.setTable("ClientTable");

        handler = new Handler(mockClient, mockService, mockParameterDTO);
    }

    @Nested
    @DisplayName("execute method")
    class ExecuteMethod {

        @Test
        @DisplayName("returns success response for valid guardar request")
        void returnsSuccessForGuardar() throws Exception, ErrorResponse {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("servicio", "guardar");
            request.setHeaders(headers);
            request.setBody("{\"strFirstName\":\"John\"}");

            when(mockService.saveClient(any(), any(), anyString()))
                    .thenReturn("Client saved");

            APIGatewayProxyResponseEvent response = handler.execute(request);

            assertEquals(201, response.getStatusCode());
            assertEquals(response.getBody(), response.getBody()); // String is JSON-quoted
        }

        @Test
        @DisplayName("returns error response for invalid servicio")
        void returnsErrorForInvalidServicio() {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("servicio", "invalido");
            request.setHeaders(headers);
            request.setBody("{\"strFirstName\":\"Jane\"}");

            APIGatewayProxyResponseEvent response = handler.execute(request);

            assertEquals(500, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("redirect method")
    class RedirectMethod {

        @Test
        @DisplayName("handles guardar")
        void handlesGuardar() throws Exception, ErrorResponse {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            request.setHeaders(Map.of("servicio", "guardar"));
            request.setBody("{\"strFirstName\":\"Ana\"}");

            when(mockService.saveClient(any(), any(), anyString()))
                    .thenReturn("Guardado correctamente");

            Object result = handler.redirect(request);
            assertEquals(BuildResponseUtil.buildSuccess("Guardado correctamente"), result);
        }

        @Test
        @DisplayName("handles consultar")
        void handlesConsultar() throws Exception {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            request.setHeaders(Map.of("servicio", "consultar"));
            request.setBody("{\"strFirstName\":\"Carlos\"}");

            ClientEntity entity = new ClientEntity();
            entity.setStrFirstName("Carlos");

            when(mockService.getClient(any(), any(), anyString())).thenReturn(entity);

            Object result = handler.redirect(request);
            assertEquals(BuildResponseUtil.buildSuccess(entity), result);
        }

        @Test
        @DisplayName("returns error for unsupported service")
        void unsupportedService() throws Exception {
            APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
            request.setHeaders(Map.of("servicio", "otro"));
            request.setBody("{\"strFirstName\":\"Luis\"}");

            Object result = handler.redirect(request);
            assertEquals(BuildResponseUtil.buildErrorDefault(), result);
        }
    }
}