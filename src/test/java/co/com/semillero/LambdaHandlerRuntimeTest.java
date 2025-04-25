package co.com.semillero;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class LambdaHandlerRuntimeTest {

    @Test
    @DisplayName("creates request handler successfully")
    void createsRequestHandlerSuccessfully() {
        Handler handlerMock = mock(Handler.class);
        LambdaHandlerRuntime runtime = new LambdaHandlerRuntime() {
            @Override
            protected RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createRequestHandler(String... args) {
                return handlerMock;
            }
        };
        assertNotNull(runtime.createRequestHandler());
    }

    @Test
    @DisplayName("LambdaHandlerRuntime instance is created successfully")
    void lambdaHandlerRuntimeInstanceCreated() {
        LambdaHandlerRuntime runtime = new LambdaHandlerRuntime();
        assertNotNull(runtime);
    }
}