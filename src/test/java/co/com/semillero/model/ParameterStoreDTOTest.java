package co.com.semillero.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterStoreDTOTest {

    @Test
    @DisplayName("Should set and get urlDynamo correctly")
    void shouldSetAndGetUrlDynamoCorrectly() {
        ParameterStoreDTO parameterStoreDTO = new ParameterStoreDTO();
        parameterStoreDTO.setUrlDynamo("https://dynamodb.example.com");

        assertEquals("https://dynamodb.example.com", parameterStoreDTO.getUrlDynamo());
    }

    @Test
    @DisplayName("Should set and get region correctly")
    void shouldSetAndGetRegionCorrectly() {
        ParameterStoreDTO parameterStoreDTO = new ParameterStoreDTO();
        parameterStoreDTO.setRegion("us-east-1");

        assertEquals("us-east-1", parameterStoreDTO.getRegion());
    }

    @Test
    @DisplayName("Should set and get arnSecre correctly")
    void shouldSetAndGetArnSecreCorrectly() {
        ParameterStoreDTO parameterStoreDTO = new ParameterStoreDTO();
        parameterStoreDTO.setArnSecre("arn:aws:secretsmanager:us-east-1:123456789012:secret:example");

        assertEquals("arn:aws:secretsmanager:us-east-1:123456789012:secret:example", parameterStoreDTO.getArnSecre());
    }

    @Test
    @DisplayName("Should set and get table correctly")
    void shouldSetAndGetTableCorrectly() {
        ParameterStoreDTO parameterStoreDTO = new ParameterStoreDTO();
        parameterStoreDTO.setTable("exampleTable");

        assertEquals("exampleTable", parameterStoreDTO.getTable());
    }

    @Test
    @DisplayName("Should handle null values for all fields")
    void shouldHandleNullValuesForAllFields() {
        ParameterStoreDTO parameterStoreDTO = new ParameterStoreDTO();
        parameterStoreDTO.setUrlDynamo(null);
        parameterStoreDTO.setRegion(null);
        parameterStoreDTO.setArnSecre(null);
        parameterStoreDTO.setTable(null);

        assertNull(parameterStoreDTO.getUrlDynamo());
        assertNull(parameterStoreDTO.getRegion());
        assertNull(parameterStoreDTO.getArnSecre());
        assertNull(parameterStoreDTO.getTable());
    }
}