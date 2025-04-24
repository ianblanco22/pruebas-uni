package co.com.semillero.repository;

import co.com.semillero.model.ParameterStoreDTO;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Slf4j
public class DynamoRepository {

    //private static final Logger logger = LoggerFactory.getLogger(DynamoRepository.class);

    //guardad entidad en Dynamo
    public <T> void save(T entity, DynamoDbEnhancedClient client, String tableName, TableSchema<T> schema) {
        log.info("Inicia guardado/actualización en DynamoDB");
        log.info("Tabla: {}", tableName);
        log.info("Registro: {}", entity);
        try {
            DynamoDbTable<T> table = client.table(tableName, schema);
            table.putItem(entity);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error("Error en save: {}", errors);
        }
    }

    //cargar una entidad desde DynamoDB
    public <T> T load(String pk, String sk, DynamoDbEnhancedClient client, String tableName, TableSchema<T> schema) {
        DynamoDbTable<T> table = client.table(tableName, schema);
        return table.getItem(r -> r.key(k -> k.partitionValue(pk).sortValue(sk)));
    }
}
