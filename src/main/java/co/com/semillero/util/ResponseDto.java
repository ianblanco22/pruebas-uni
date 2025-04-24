/**
 * DTO ResponseDto
 */

package co.com.semillero.util;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Data;

import java.util.List;

/**
 * Metodo encargado de definir el DTO que se usa para el formato de respuesta a consultas a las lambdas.
 * <p>
 * ResponseDto
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
@Data
@Introspected
@SerdeImport(ResponseDto.class)
public class ResponseDto {

    private String statusCode;
    private String message;
    private List<String> errorDetail;
    private Object result;
}
