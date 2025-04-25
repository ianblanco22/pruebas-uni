package co.com.semillero.repositoryTest;

import co.com.semillero.constants.ParameterKey;
import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.model.ParameterStoreDTO;
import co.com.semillero.repository.ParameterStoreRepository;
import co.com.semillero.util.ParameterStoreUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mockStatic;

class ParameterStoreRepositoryTest {

    private ParameterStoreRepository parameterStoreRepository;

    @BeforeEach
    void setUp() {
        parameterStoreRepository = new ParameterStoreRepository();
    }

    @Test
    @DisplayName("Returns valid ParameterStoreDTO when all keys are present")
    void returnsValidParameterStoreDTOWhenAllKeysArePresent() {
        try (MockedStatic<ParameterStoreUtil> mockedUtil = mockStatic(ParameterStoreUtil.class)) {
            Map<String, String> mockParameters = new HashMap<>();
            mockParameters.put(ParameterKey.NOMBRE_TABLA.getFullKey(), "TestTable");
            mockParameters.put(ParameterKey.REGION.getFullKey(), "us-east-1");
            mockParameters.put(ParameterKey.ARN_SECRET.getFullKey(), "arn:aws:secretsmanager:...");
            mockParameters.put(ParameterKey.URL_DYNAMO.getFullKey(), "https://dynamodb.us-east-1.amazonaws.com");

            mockedUtil.when(() -> ParameterStoreUtil.getParameters(ParameterKey.BASE_PATH)).thenReturn(mockParameters);

            ParameterStoreDTO result = parameterStoreRepository.getParameter();

            assertEquals("TestTable", result.getTable());
            assertEquals("us-east-1", result.getRegion());
            assertEquals("arn:aws:secretsmanager:...", result.getArnSecre());
            assertEquals("https://dynamodb.us-east-1.amazonaws.com", result.getUrlDynamo());
        }
    }

    @Test
    @DisplayName("Returns ParameterStoreDTO with null fields when keys are missing")
    void returnsParameterStoreDTOWithNullFieldsWhenKeysAreMissing() {
        try (MockedStatic<ParameterStoreUtil> mockedUtil = mockStatic(ParameterStoreUtil.class)) {
            Map<String, String> mockParameters = new HashMap<>();

            mockedUtil.when(() -> ParameterStoreUtil.getParameters(ParameterKey.BASE_PATH)).thenReturn(mockParameters);

            ParameterStoreDTO result = parameterStoreRepository.getParameter();

            assertNull(result.getTable());
            assertNull(result.getRegion());
            assertNull(result.getArnSecre());
            assertNull(result.getUrlDynamo());
        }
    }

    @Test
    @DisplayName("Handles null response from ParameterStoreUtil gracefully")
    void handlesNullResponseFromParameterStoreUtilGracefully() throws ErrorResponse {
        try (MockedStatic<ParameterStoreUtil> mockedUtil = mockStatic(ParameterStoreUtil.class)) {
            mockedUtil.when(() -> ParameterStoreUtil.getParameters(ParameterKey.BASE_PATH)).thenReturn(null);

            ParameterStoreDTO result = parameterStoreRepository.getParameter();

            assertNull(result.getTable());
            assertNull(result.getRegion());
            assertNull(result.getArnSecre());
            assertNull(result.getUrlDynamo());
        }
    }
}