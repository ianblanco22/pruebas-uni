package co.com.semillero.repository;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.model.ParameterStoreDTO;
import co.com.semillero.util.Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@AllArgsConstructor
public class DynamoMapperRepository {
    private String dynamoRegion;

    public DynamoDbEnhancedClient getClient() {
        log.info("Entrando a getClient");
        DynamoDbEnhancedClient enhancedClient;
        try {
            var client = DynamoDbClient.builder().region(Region.of(dynamoRegion))
                    .credentialsProvider(
                            SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.getStringValue().isPresent() ?
                                    ContainerCredentialsProvider.builder().build() : EnvironmentVariableCredentialsProvider.create())
                    .build();

            try {
                enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();

                log.info("enhancedClient en getClient: {}", Util.object2String(enhancedClient));

                return enhancedClient;
            }catch (NullPointerException e) {
                throw new NullPointerException("Client cannot be null");
            }

        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            return null;
        }
    }
}


