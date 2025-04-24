package co.com.semillero.service;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.model.Client;
import co.com.semillero.model.entity.ClientEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

public interface IDynamoService {
    String saveClient(DynamoDbEnhancedClient client, Client dto, String table) throws ErrorResponse;
    ClientEntity getClient(DynamoDbEnhancedClient client, Client dto, String table);
}