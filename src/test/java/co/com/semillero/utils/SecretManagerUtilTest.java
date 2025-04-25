package co.com.semillero.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecretManagerUtilTest {

    @Test
    @DisplayName("ReturnsJsonObjectWhenSecretExists")
    void returnsJsonObjectWhenSecretExists() {
        SecretsManagerClient mockClient = mock(SecretsManagerClient.class);
        GetSecretValueResponse mockResponse = mock(GetSecretValueResponse.class);

        when(mockClient.getSecretValue(any(GetSecretValueRequest.class)))
                .thenReturn(mockResponse);
        when(mockResponse.secretString())
                .thenReturn("{\"key\":\"value\"}");

        JsonObject result = SecretManagerUtil.getSecret("testSecret");

        assertNotNull(result);
        assertEquals("value", result.get("key").getAsString());
    }

    @Test
    @DisplayName("ReturnsNullWhenKeyDoesNotExist")
    void returnsNullWhenKeyDoesNotExist() {
        JsonObject mockSecret = JsonParser.parseString("{\"key\":\"value\"}").getAsJsonObject();

        String result = SecretManagerUtil.getSecretValue(mockSecret, "nonExistentKey");

        assertNull(result);
    }

    @Test
    @DisplayName("ReturnsValueWhenKeyExists")
    void returnsValueWhenKeyExists() {
        JsonObject mockSecret = JsonParser.parseString("{\"key\":\"value\"}").getAsJsonObject();

        String result = SecretManagerUtil.getSecretValue(mockSecret, "key");

        assertEquals("value", result);
    }

    @Test
    @DisplayName("ThrowsExceptionWhenSecretNameIsNull")
    void throwsExceptionWhenSecretNameIsNull() {
        assertThrows(NullPointerException.class, () -> SecretManagerUtil.getSecret(null));
    }

    @Test
    @DisplayName("ThrowsExceptionWhenSecretStringIsInvalidJson")
    void throwsExceptionWhenSecretStringIsInvalidJson() {
        SecretsManagerClient mockClient = mock(SecretsManagerClient.class);
        GetSecretValueResponse mockResponse = mock(GetSecretValueResponse.class);

        when(mockClient.getSecretValue(any(GetSecretValueRequest.class)))
                .thenReturn(mockResponse);
        when(mockResponse.secretString())
                .thenReturn("invalidJson");

        assertThrows(IllegalStateException.class, () -> SecretManagerUtil.getSecret("testSecret"));
    }
}