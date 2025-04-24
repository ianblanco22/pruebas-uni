/**
 * Enum de mensajes de respuesta para BuildResponseUtil
 */
package co.com.semillero.constants;

/**
 * Enum de mensajes de respuesta para BuildResponseUtil
 * <p>
 * MessagesEnum
 * <p>
 * Desarrollo ATH - Aval Pay Center
 * <p>
 * Creado él: 19 de mayo de 2023
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
public enum MessagesEnum {
    SUCCESS_RESPONSE("2000", "Success", 201),
    ERROR_CONNECTION_SNS("No se esta generando la conexion con el SNS",
            "Error de conexion SNS",
            502),
    DEFAULT_ERROR_RESPONSE("5001",
            "Servicio no disponible, por favor intente nuevamente",
            500),
    SUCCESS_REDIRECT_RESPONSE("3002", "Success Redirect", 302),
    ;

    private String code;
    private String message;
    private int httpCode;

    private MessagesEnum(String code, String message, int httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
