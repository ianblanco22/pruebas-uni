package co.com.semillero.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

/**
 * Clase para el manejo de tareas con Secrets Manager
 * <p>
 * SecretManagerUtil
 * <p>
 * Desarrollo ATH - Aval Pay Center
 * <p>
 * Creado él: 5 de mayo de 2023
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
public class SecretManagerUtil {
    /**
     * Constructor
     */
    private SecretManagerUtil() {
    }

    /**
     * Método que devuelve un JSON String con los valores de los secretos
     * Creando una conexion con AWSSecretManager, hace la consulta de secretos segun el nombre del secreto
     * devuelve un JsonObject con los valores obtenidos
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param secretName Nombre del secreto a obtener
     * @return String JSON String del secreto
     */
    public static JsonObject getSecret(String secretName) {
        SecretsManagerClient secretsManagerClient = SecretsManagerClient.create();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)  // Nombre del secreto
                .build();

        GetSecretValueResponse getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
        String secretString = getSecretValueResponse.secretString();

        // Convertir el valor del secreto en un JsonObject
        JsonObject secretJsonObject = JsonParser.parseString(secretString).getAsJsonObject();
        return secretJsonObject;

    }

    /**
     * Método que devuelve el valor usando una variable que se le asigna asi como el JsonObject.
     * Obtiene el valor usando la variable enviada
     * Este metodo recibe los parametros cuando se llama a la libreria
     * @return String JSON String del valor
     */
    public static String getSecretValue(JsonObject secret, String clave) {
        if (secret.has(clave)) {
            return secret.get(clave).getAsString();
        } else {
            return null;
        }

    }
}
