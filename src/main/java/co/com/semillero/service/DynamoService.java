package co.com.semillero.service;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.mapper.ClientMapper;
import co.com.semillero.model.Client;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.repository.DynamoRepository;
import co.com.semillero.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

@Slf4j
public class DynamoService implements IDynamoService {

    DynamoRepository dynamoRepository = new DynamoRepository();
    ClientMapper clientMapper = new ClientMapper();

    //private static final Logger logger = LoggerFactory.getLogger(DynamoService.class);

    @Override
    public String saveClient(DynamoDbEnhancedClient client, Client dto, String table) throws ErrorResponse {
        log.info("Guardando cliente en la tabla: {}", table);
        log.info("cliente nombre:",dto.getStrFirstName());
        log.info("Client: {}", Util.object2String(dto));

        try {
            ClientEntity entity = clientMapper.clientMapper(dto);
            dynamoRepository.save(entity, client, table, ClientEntity.SCHEMA_CLIENT);
            return "Cliente guardado correctamente";

        } catch (RuntimeException e) {
            throw new ErrorResponse(new Exception("Error al guardar el cliente", e));
        }

    }

    @Override
    public ClientEntity getClient(DynamoDbEnhancedClient client, Client dto, String table) {
        String id = dto.getKey().getStrTypeLlave() + "_" + dto.getKey().getStrIdLlave();
        log.info("Consultando cliente con ID: {} en tabla: {}", id, table);
        return dynamoRepository.load(id, id, client, table, ClientEntity.SCHEMA_CLIENT);
    }
}
