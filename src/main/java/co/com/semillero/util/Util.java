/**
 * Clase Util encargada de transformar objetos en string o string en objetos
 */
package co.com.semillero.util;

import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import io.micronaut.context.ApplicationContext;
import io.micronaut.serde.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase Util encargada de transformar objetos en string o string en objetos
 * <p>
 * Util
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
public class Util {
    /**
     * intancia de ObjectMapper para mapear objetos
     */
    private static ObjectMapper objectMapper;

    private static StringWriter errors = new StringWriter();

    /**
     * Transforma un string en un objeto según la clase
     * usando un Jsonbuilder se encarga de serializar los valores del json a un objeto primitivo
     * El String json es el string destinado a volverse objeto y el class especifica el tipo de objeto que se volvera
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static Object string2object(String json, Class<? extends Object> classOfT) {
        Object objeto;
        try {
            objectMapper = ApplicationContext.run().getBean(ObjectMapper.class);
            objeto = objectMapper.readValue(json, classOfT);
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
            throw new RuntimeException(e);
        }
        return objeto;
    }

    public static Object string2objectWhitNulls(String json, Class<? extends Object> classOfT){
        return string2object(json, classOfT);
    }


    /**
     * transforma un objeto en un string usando las clases de Gson
     * Este metodo utiliza los metodos nativos de Gson
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param typeObject
     * @return
     */
    public static String object2String(Object typeObject){
        String json;
        try {
            objectMapper = ApplicationContext.run().getBean(ObjectMapper.class);
            json = objectMapper.writeValueAsString(typeObject);
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors.toString());
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * Metodo encargado de transformar una cadena a UTF-8 para tema de caracteres especiales
     *
     * @param cadena
     * @return
     */
    public static String stringUTF8(String cadena) {
        byte[] utf8Bytes = cadena.getBytes(StandardCharsets.UTF_8);
        return new String(utf8Bytes, StandardCharsets.UTF_8);
    }

    /**
     * Parametriza el objeto en un string incluyendo los campos que se encuentren nulos
     * Utiliza los metodos nativos de Gson
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param typeObject
     * @return
     */
    public static String object2StringWithNulls(Object typeObject){
        /*gson = new Gson().newBuilder().serializeNulls().create();
        return gson.toJson(typeObject);*/
        return object2String(typeObject);
    }

    /**
     * transforma un DynamoDb JSON a un JSON
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param image
     * @return un String con el Respectivo JSON transformado
     */
    public static String convertToJson(Map<String, AttributeValue> image) throws IOException {
        Util util = new Util();
        Map<String, Object> standardMap = new HashMap<>();
        for (Map.Entry<String, AttributeValue> entry : image.entrySet()) {
            standardMap.put(entry.getKey(), util.convertAttributeValue(entry.getValue()));
        }
        return objectMapper.writeValueAsString(standardMap);
    }

    /**
     * transforma AttributeValues en sus valores normales
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param value
     * @return un objeto con los respectivos palores de los atributos
     */
    private Object convertAttributeValue(AttributeValue value) {
        if (value.getS() != null) {
            return value.getS();
        } else if (value.getN() != null) {
            return value.getN();
        } else if (value.getBOOL() != null) {
            return value.getBOOL();
        } else if (value.getSS() != null) {
            return value.getSS();
        } else if (value.getNS() != null) {
            return value.getNS();
        } else if (value.getM() != null) {
            Map<String, Object> map = new HashMap<>();
            value.getM().forEach((k, v) -> map.put(k, convertAttributeValue(v)));
            return map;
        } else if (value.getL() != null) {
            return value.getL().stream().map(this::convertAttributeValue).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Devuelve el valor de una variable de entorno del sistema
     * Utiliza metodos de System para consultar variables
     * Este metodo recibe los parametros cuando se llama a la libreria
     *
     * @param param
     * @return
     */
    public static String getEnviromentVariable(String param) {
        return System.getenv().get(param);
    }

    /**
     * Método utilizado para generar la fecha en los índices de Opensearch
     * <p>
     * Crea una fecha en formato yyyy-MM-dd HH:mm:ss.SSS con la zona horaria de Colombia
     * Restando 5 horas a la hora actual de los servidores de AWS.
     *
     * @return String con la fecha generada en el formato adecuado.
     */
    public static String createDate(String pattern) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - 5);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(cal.getTime());
    }

    public static String createDate() {

        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - 5);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String fechaLib = simpleDateFormat.format(cal.getTime());

        return fechaLib;
    }
}