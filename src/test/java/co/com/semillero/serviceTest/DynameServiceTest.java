package co.com.semillero.serviceTest;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.mapper.ClientMapper;
import co.com.semillero.model.Client;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.model.entity.KeyEntity;
import co.com.semillero.repository.DynamoRepository;
import co.com.semillero.service.DynamoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamoServiceTest {

    private DynamoService dynamoService;

    @Mock
    private DynamoRepository dynamoRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dynamoService = new DynamoService();
        dynamoService.dynamoRepository = dynamoRepository;
        dynamoService.clientMapper = clientMapper;
    }

    @Test
    @DisplayName("Guarda cliente correctamente cuando se proporciona entrada válida")
    void guardaClienteCorrectamenteCuandoSeProporcionaEntradaValida() throws ErrorResponse {
        Client mockDto = new Client();

        String type = "NRIC";
        String id = "7889";

        KeyEntity key = new KeyEntity();

        key.setStrIdLlave(id);
        key.setStrTypeLlave(type);

        mockDto.setStrFirstName("John");
        mockDto.setKey(key);
        String table = "TestTable";

        ClientEntity mockEntity = new ClientEntity();
        when(clientMapper.clientMapper(mockDto)).thenReturn(mockEntity);

        String result = dynamoService.saveClient(dynamoDbEnhancedClient, mockDto, table);

        assertEquals("Cliente guardado correctamente", result);
        verify(dynamoRepository).save(mockEntity, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT);
    }

    @Test
    @DisplayName("Lanza ErrorResponse cuando falla el guardado del cliente")
    void lanzaErrorResponseCuandoFallaElGuardadoDelCliente() {
        Client mockDto = new Client();
        String type = "NRIC";
        String id = "7889";

        KeyEntity key = new KeyEntity();

        key.setStrIdLlave(id);
        key.setStrTypeLlave(type);

        mockDto.setStrFirstName("John");
        mockDto.setKey(key);
        String table = "TestTable";

        ClientEntity mockEntity = new ClientEntity();
        when(clientMapper.clientMapper(mockDto)).thenReturn(mockEntity);
        doThrow(new RuntimeException("Save failed")).when(dynamoRepository).save(mockEntity, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT);

        assertThrows(ErrorResponse.class, () -> dynamoService.saveClient(dynamoDbEnhancedClient, mockDto, table));
    }

    @Test
    @DisplayName("Devuelve entidad cliente cuando se proporciona entrada válida para getClient")
    void devuelveEntidadClienteCuandoSeProporcionaEntradaValidaParaGetClient() {
        Client mockDto = new Client();
        String type = "NRIC";
        String id = "7889";

        KeyEntity key = new KeyEntity();

        key.setStrIdLlave(id);
        key.setStrTypeLlave(type);

        mockDto.setStrFirstName("John");
        mockDto.setKey(key);
        String table = "TestTable";
        String expectedId = "type_id";

        ClientEntity mockEntity = new ClientEntity();
        when(dynamoRepository.load(expectedId, expectedId, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT)).thenReturn(mockEntity);

        ClientEntity result = dynamoService.getClient(dynamoDbEnhancedClient, mockDto, table);

        assertEquals(mockEntity, result);
    }

    @Test
    @DisplayName("Devuelve null cuando no se encuentra el cliente en getClient")
    void devuelveNullCuandoNoSeEncuentraElClienteEnGetClient() {
        Client mockDto = new Client();
        String type = "NRIC";
        String id = "7889";

        KeyEntity key = new KeyEntity();

        key.setStrIdLlave(id);
        key.setStrTypeLlave(type);

        mockDto.setStrFirstName("John");
        mockDto.setKey(key);
        String table = "TestTable";
        String expectedId = "type_id";

        when(dynamoRepository.load(expectedId, expectedId, dynamoDbEnhancedClient, table, ClientEntity.SCHEMA_CLIENT)).thenReturn(null);

        ClientEntity result = dynamoService.getClient(dynamoDbEnhancedClient, mockDto, table);

        assertNull(result);
    }
}