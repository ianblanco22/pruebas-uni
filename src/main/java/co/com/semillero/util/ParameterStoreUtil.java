package co.com.semillero.util;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase encargada de conectarse con el parameter Store
 * <p>
 * ExecutePaymentRq
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
public class ParameterStoreUtil {

    /**
     * Método getParameters trae los parámetros del parameterStore relacionados con el path
     * primero crea una conexion con el cliente AWSSimpleSystemsManagement
     * Luego define el path que se usara para enviar el mensaje
     * En el GetParametersByPathResult   se almacena el resultado de la consulta
     * y luego se mapea en una lista de parametros
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param path
     * @return
     */
    public static Map<String, String> getParameters(String path) {

        SsmClient ssmClient = SsmClient.create();

        GetParametersByPathRequest getParametersByPathRequest = GetParametersByPathRequest.builder().
                path(path).
                recursive(true).
                build();

        List<Parameter> parameters = ssmClient.getParametersByPathPaginator(getParametersByPathRequest).stream()
                .flatMap(page -> page.parameters().stream())
                .collect(Collectors.toList());


        Map<String, String> values = new HashMap<>();
        values = parameters.stream()
                .collect(Collectors.toMap(Parameter::name, Parameter::value));

        return values;
    }

}
