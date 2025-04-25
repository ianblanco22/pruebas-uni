package co.com.semillero.repositoryTest;

import co.com.semillero.repository.DynamoMapperRepository;
import co.com.semillero.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamoMapperRepositoryTest {

    @Mock
    private Util util;

    private DynamoMapperRepository dynamoMapperRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dynamoMapperRepository = new DynamoMapperRepository("us-east-1");
    }

    @Nested
    @DisplayName("getClient")
    class GetClient {

        @Test
        @DisplayName("Should return DynamoDbEnhancedClient successfully")
        void shouldReturnDynamoDbEnhancedClientSuccessfully() {
            System.setProperty(SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.property(), "true");

            DynamoDbEnhancedClient client = dynamoMapperRepository.getClient();

            assertNotNull(client);
        }

        @Test
        @DisplayName("Should return null when exception occurs")
        void shouldReturnNullWhenExceptionOccurs() {
            DynamoMapperRepository repositoryWithInvalidRegion = new DynamoMapperRepository("invalid-region");

            DynamoDbEnhancedClient client = repositoryWithInvalidRegion.getClient();

            assertNull(client);
        }

        @Test
        @DisplayName("Should use ContainerCredentialsProvider when AWS_CONTAINER_CREDENTIALS_FULL_URI is set")
        void shouldUseContainerCredentialsProviderWhenEnvVariableIsSet() {
            System.setProperty(SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.property(), "true");

            DynamoDbClient dynamoDbClient = mock(DynamoDbClient.class);
            DynamoDbEnhancedClient enhancedClient = mock(DynamoDbEnhancedClient.class);

            when(DynamoDbClient.builder().region(Region.of("us-east-1"))
                    .credentialsProvider(ContainerCredentialsProvider.builder().build())
                    .build()).thenReturn(dynamoDbClient);

            when(DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build()).thenReturn(enhancedClient);

            DynamoDbEnhancedClient client = dynamoMapperRepository.getClient();

            assertNotNull(client);
        }

        @Test
        @DisplayName("Should use EnvironmentVariableCredentialsProvider when AWS_CONTAINER_CREDENTIALS_FULL_URI is not set")
        void shouldUseEnvironmentVariableCredentialsProviderWhenEnvVariableIsNotSet() {
            System.clearProperty(SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.property());

            DynamoDbClient dynamoDbClient = mock(DynamoDbClient.class);
            DynamoDbEnhancedClient enhancedClient = mock(DynamoDbEnhancedClient.class);

            when(DynamoDbClient.builder().region(Region.of("us-east-1"))
                    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                    .build()).thenReturn(dynamoDbClient);

            when(DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build()).thenReturn(enhancedClient);

            DynamoDbEnhancedClient client = dynamoMapperRepository.getClient();

            assertNotNull(client);
        }
    }
}