package co.com.semillero.serviceTest;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.mapper.ClientMapper;
import co.com.semillero.model.Client;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.repository.DynamoRepository;
import co.com.semillero.service.DynamoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamoServiceTest {

    @Mock
    private DynamoRepository dynamoRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @InjectMocks
    private DynamoService dynamoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("saveClient")
    class SaveClient {

        @Test
        @DisplayName("Should save client successfully")
        void shouldSaveClientSuccessfully() throws ErrorResponse {
            Client client = mock(Client.class);
            ClientEntity clientEntity = mock(ClientEntity.class);
            String table = "testTable";

            when(clientMapper.clientMapper(client)).thenReturn(clientEntity);

            String result = dynamoService.saveClient(dynamoDbEnhancedClient, client, table);

            assertEquals("Cliente guardado correctamente", result);
            verify(dynamoRepository).save(clientEntity, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT);
        }

        @Test
        @DisplayName("Should throw ErrorResponse when save fails")
        void shouldThrowErrorResponseWhenSaveFails() throws ErrorResponse {
            Client client = mock(Client.class);
            String table = "testTable";

            when(clientMapper.clientMapper(client)).thenThrow(new RuntimeException("Save failed"));

            assertThrows(ErrorResponse.class, () -> dynamoService.saveClient(dynamoDbEnhancedClient, client, table));
        }
    }

    @Nested
    @DisplayName("getClient")
    class GetClient {

        @Test
        @DisplayName("Should retrieve client successfully")
        void shouldRetrieveClientSuccessfully() {
            Client client = mock(Client.class);
            ClientEntity clientEntity = mock(ClientEntity.class);
            String table = "testTable";
            String id = "key_type_key_id";

            when(client.getKey().getStrTypeLlave()).thenReturn("key_type");
            when(client.getKey().getStrIdLlave()).thenReturn("key_id");
            when(dynamoRepository.load(id, id, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT))
                    .thenReturn(clientEntity);

            ClientEntity result = dynamoService.getClient(dynamoDbEnhancedClient, client, table);

            assertEquals(clientEntity, result);
            verify(dynamoRepository).load(id, id, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT);
        }

        @Test
        @DisplayName("Should return null when client not found")
        void shouldReturnNullWhenClientNotFound() {
            Client client = mock(Client.class);
            String table = "testTable";
            String id = "key_type_key_id";

            when(client.getKey().getStrTypeLlave()).thenReturn("key_type");
            when(client.getKey().getStrIdLlave()).thenReturn("key_id");
            when(dynamoRepository.load(id, id, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT))
                    .thenReturn(null);

            ClientEntity result = dynamoService.getClient(dynamoDbEnhancedClient, client, table);

            assertNull(result);
        }
    }
}