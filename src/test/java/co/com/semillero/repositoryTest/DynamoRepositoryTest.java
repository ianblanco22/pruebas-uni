package co.com.semillero.repositoryTest;

import co.com.semillero.repository.DynamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamoRepositoryTest {

    @Mock
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Mock
    private DynamoDbTable<Object> dynamoDbTable;

    private DynamoRepository dynamoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dynamoRepository = new DynamoRepository();
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        @DisplayName("Should save entity successfully")
        void shouldSaveEntitySuccessfully() {
            Object entity = new Object();
            String tableName = "testTable";
            TableSchema<Object> schema = mock(TableSchema.class);

            when(dynamoDbEnhancedClient.table(tableName, schema)).thenReturn(dynamoDbTable);

            dynamoRepository.save(entity, dynamoDbEnhancedClient, tableName, schema);

            verify(dynamoDbTable).putItem(entity);
        }

        @Test
        @DisplayName("Should log error when save fails")
        void shouldLogErrorWhenSaveFails() {
            Object entity = new Object();
            String tableName = "testTable";
            TableSchema<Object> schema = mock(TableSchema.class);

            when(dynamoDbEnhancedClient.table(tableName, schema)).thenReturn(dynamoDbTable);
            doThrow(new RuntimeException("Save failed")).when(dynamoDbTable).putItem(entity);

            dynamoRepository.save(entity, dynamoDbEnhancedClient, tableName, schema);

            verify(dynamoDbTable).putItem(entity);
        }
    }

    @Nested
    @DisplayName("load")
    class Load {

        @Test
        @DisplayName("Should load entity successfully when valid keys are provided")
        void shouldLoadEntitySuccessfullyWhenValidKeysAreProvided() {
            String pk = "validPartitionKey";
            String sk = "validSortKey";
            String tableName = "testTable";
            TableSchema<Object> schema = mock(TableSchema.class);
            Object expectedEntity = new Object();

            when(dynamoDbEnhancedClient.table(tableName, schema)).thenReturn(dynamoDbTable);
            when(dynamoDbTable.getItem(any(Function.class))).thenReturn(expectedEntity);

            Object result = dynamoRepository.load(pk, sk, dynamoDbEnhancedClient, tableName, schema);

            assertEquals(expectedEntity, result);
        }

        @Test
        @DisplayName("Should return null when entity not found")
        void shouldReturnNullWhenEntityNotFound() {
            String pk = "partitionKey";
            String sk = "sortKey";
            String tableName = "testTable";
            TableSchema<Object> schema = mock(TableSchema.class);

            when(dynamoDbEnhancedClient.table(tableName, schema)).thenReturn(dynamoDbTable);
            when(dynamoDbTable.getItem(any(Function.class))).thenReturn(null);

            Object result = dynamoRepository.load(pk, sk, dynamoDbEnhancedClient, tableName, schema);

            assertNull(result);
        }
        @Test
        @DisplayName("Should log error and return null when exception occurs")
        void shouldLogErrorAndReturnNullWhenExceptionOccurs() {
            String pk = "partitionKey";
            String sk = "sortKey";
            String tableName = "testTable";
            TableSchema<Object> schema = mock(TableSchema.class);

            when(dynamoDbEnhancedClient.table(tableName, schema)).thenReturn(dynamoDbTable);
            when(dynamoDbTable.getItem(any(Function.class))).thenThrow(new RuntimeException("DynamoDB error"));

            Object result = dynamoRepository.load(pk, sk, dynamoDbEnhancedClient, tableName, schema);

            assertNull(result);
        }
    }

}