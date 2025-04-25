package co.com.semillero;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.mapper.ClientMapper;
import co.com.semillero.model.Client;
import co.com.semillero.model.ParameterStoreDTO;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.repository.DynamoMapperRepository;
import co.com.semillero.repository.ParameterStoreRepository;
import co.com.semillero.service.DynamoService;
import co.com.semillero.util.BuildResponseUtil;
import co.com.semillero.util.Util;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import java.io.IOException;

@Slf4j
@Introspected
public class Handler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    ParameterStoreRepository parameterRepository;
    ParameterStoreDTO parameterDTO;
    DynamoMapperRepository dynamoMapperRepository;
    DynamoDbEnhancedClient dynamoClient;
    DynamoService service;

    // Constructor por defecto para producción
    public Handler() {
        this.parameterRepository = new ParameterStoreRepository();
        this.parameterDTO = parameterRepository.getParameter();
        this.dynamoMapperRepository = new DynamoMapperRepository(parameterDTO.getRegion());
        this.dynamoClient = dynamoMapperRepository.getClient();
        this.service = new DynamoService();
    }

    // Constructor para pruebas unitarias (inyección de mocks)
    public Handler(DynamoDbEnhancedClient client, DynamoService service, ParameterStoreDTO parameterDTO) {
        this.dynamoClient = client;
        this.service = service;
        this.parameterDTO = parameterDTO;
    }

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        try {
            log.info("parameterStore en el execute: {} ", Util.object2String(parameterDTO));
            return BuildResponseUtil.buildSuccess(redirect(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object redirect(APIGatewayProxyRequestEvent input) throws IOException {
        try {
            log.info("Iniciando ejecución Lambda...");
            String servicio = input.getHeaders().get("servicio");

            log.info("body: {}", input.getBody());
            log.info("Servicio: {}, Tabla: {}", servicio, parameterDTO.getTable());

            Client client = (Client) Util.string2object(input.getBody(), Client.class);

            switch (servicio) {
                case "guardar":
                    log.info("objeto client: {}", Util.object2String(client));
                    String msg = service.saveClient(dynamoClient, client, parameterDTO.getTable());
                    return BuildResponseUtil.buildSuccess(msg);

                case "consultar":
                    ClientEntity entity = service.getClient(dynamoClient, client, parameterDTO.getTable());
                    return BuildResponseUtil.buildSuccess(entity);

                default:
                    throw new RuntimeException("Servicio no disponible");
            }

        } catch (ErrorResponse e) {
            log.info("ErrorResponse capturado: {}", e.getMessage(), e);
            return BuildResponseUtil.buildErrorDefault();

        } catch (Exception e) {
            log.info("Error en la ejecución del handler: {}", e.getMessage(), e);
            return BuildResponseUtil.buildErrorDefault();
        }
    }
}