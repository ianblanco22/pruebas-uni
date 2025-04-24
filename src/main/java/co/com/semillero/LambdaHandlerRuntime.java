package co.com.semillero;

import co.com.semillero.mapper.ClientMapper;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

@Slf4j
public class LambdaHandlerRuntime extends
        AbstractMicronautLambdaRuntime<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent,
                APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    //private static final Logger logger = LoggerFactory.getLogger(ClientMapper.class);

    public static void main(String[] args) throws MalformedURLException {
        log.info("Entro a LambdaHandlerRuntime main()");
        new LambdaHandlerRuntime().run(args);
    }

    /**
     * Método encargado de invocar el LambdaHandler
     *
     * @param args
     * @return
     */
    @Override
    protected RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
    createRequestHandler(String... args) {
        return new Handler();
    }
}