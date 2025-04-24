
package co.com.semillero.util;



import co.com.semillero.constants.MessagesEnum;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de construir una respuesta standard para el front
 * <p>
 * BuildResponseUtil
 * <p>
 * Desarrollo ATH - Aval Pay Center
 * <p>
 * Creado él: 05 de mayo de 2023
 *
 * @author Luis F Herreño
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: Migración Aval Pay Center
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
@Slf4j
public class BuildResponseUtil {

    /**
     * Constructor de clase
     * Define el mapa de headers para el objeto
     */
    private BuildResponseUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put("Access-Control-Allow-Methods", "GET,OPTIONS,POST,PUT,DELETE");

    }

    /**
     * Mapea un DTO ResponseDto con respuesta de Success y lo guarda en un objeto APIGatewayProxyResponseEvent
     * Sin headers
     * Este metodo recibe los parametros cuando se llama a la libreria
     * @param response
     * @return
     */
    public static APIGatewayProxyResponseEvent buildSuccess(Object response) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(MessagesEnum.SUCCESS_RESPONSE.getMessage());
        responseDto.setStatusCode(MessagesEnum.SUCCESS_RESPONSE.getCode());
        responseDto.setResult(response);

        return new APIGatewayProxyResponseEvent()
                .withBody(Util.object2String(responseDto))
                .withStatusCode(MessagesEnum.SUCCESS_RESPONSE.getHttpCode()).withHeaders(headers);
    }

    /**
     * Mapea un DTO ResponseDto con respuesta de Error y lo guarda en un objeto APIGatewayProxyResponseEvent
     * Este metodo recibe los parametros cuando se llama a la libreria
     * @return
     */
    public static APIGatewayProxyResponseEvent buildErrorDefault() throws IOException {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage());
        responseDto.setStatusCode(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode());

        return new APIGatewayProxyResponseEvent()
                .withBody(Util.object2String(responseDto))
                .withStatusCode(MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode())
                .withHeaders(headers);
    }


}
