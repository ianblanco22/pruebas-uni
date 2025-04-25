package co.com.semillero;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LambdaHandlerRuntimeTest {

    @Test
    @DisplayName("creates request handler successfully")
    void createsRequestHandlerSuccessfully() {
        LambdaHandlerRuntime runtime = new LambdaHandlerRuntime();
        assertNotNull(runtime.createRequestHandler());
    }

    @Test
    @DisplayName("main method runs without exceptions")
    void mainMethodRunsWithoutExceptions() {
        LambdaHandlerRuntime runtime = new LambdaHandlerRuntime();
        String[] args = {};
        try {
            runtime.run(args);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during runtime execution", e);
        }
    }
}