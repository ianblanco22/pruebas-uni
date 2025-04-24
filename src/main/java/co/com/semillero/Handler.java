package co.com.semillero;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.mapper.ClientMapper;
import co.com.semillero.model.Client;
import co.com.semillero.model.ParameterStoreDTO;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.repository.DynamoMapperRepository;
import co.com.semillero.repository.DynamoRepository;
import co.com.semillero.repository.ParameterStoreRepository;
import co.com.semillero.service.DynamoService;
import co.com.semillero.util.BuildResponseUtil;
import co.com.semillero.util.Util;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import java.io.IOException;

@Slf4j
@Introspected
public class Handler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    //private static final Logger logger = LoggerFactory.getLogger(ClientMapper.class);

    ParameterStoreRepository parameterRepository = new ParameterStoreRepository();
    ParameterStoreDTO parameterDTO = parameterRepository.getParameter();
    DynamoMapperRepository dynamoMapperRepository = new DynamoMapperRepository(parameterDTO.getRegion());
    DynamoDbEnhancedClient dynamoClient = dynamoMapperRepository.getClient();
    DynamoService service = new DynamoService();

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
            // Headers esperados

            String servicio = input.getHeaders().get("servicio");

            log.info("body:"+input.getBody());
            log.info("Servicio: {}, Tabla: {}", servicio, parameterDTO.getTable());

            Client client = (Client) Util.string2object(input.getBody(), Client.class);

            // Redirección por servicio
            if ("guardar".equals(servicio)) {
                log.info("objeto client: {}", Util.object2String(client));
                log.info("tabla:",parameterDTO.getTable());
                log.info("cliente nombre:",client.getStrFirstName());
                log.info("ParameterStoreDTO:",Util.object2String(parameterDTO));

                String msg = service.saveClient(dynamoClient, client, parameterDTO.getTable());
                return BuildResponseUtil.buildSuccess(msg);
            } else if ("consultar".equals(servicio)) {
                ClientEntity entity = service.getClient(dynamoClient, client, parameterDTO.getTable());
                return BuildResponseUtil.buildSuccess(entity);
            } else {
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