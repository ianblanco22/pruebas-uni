package co.com.semillero.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import static org.junit.jupiter.api.Assertions.*;

class KeyEntityTest {

    @Test
    @DisplayName("Should correctly map attributes to schema")
    void shouldCorrectlyMapAttributesToSchema() {
        TableSchema<KeyEntity> schema = KeyEntity.SCHEMA;

        assertNotNull(schema);
        KeyEntity keyEntity = new KeyEntity();
        keyEntity.setStrIdLlave("12345");
        keyEntity.setStrTypeLlave("TypeA");

        assertEquals("12345", keyEntity.getStrIdLlave());
        assertEquals("TypeA", keyEntity.getStrTypeLlave());
    }

    @Test
    @DisplayName("Should create new KeyEntity instance using schema")
    void shouldCreateNewKeyEntityInstanceUsingSchema() {
        KeyEntity keyEntity = new KeyEntity();

        assertNotNull(keyEntity);
        assertTrue(keyEntity instanceof KeyEntity);
    }

    @Test
    @DisplayName("Should set and get strIdLlave correctly")
    void shouldSetAndGetStrIdLlaveCorrectly() {
        KeyEntity keyEntity = new KeyEntity();
        keyEntity.setStrIdLlave("12345");

        assertEquals("12345", keyEntity.getStrIdLlave());
    }

    @Test
    @DisplayName("Should set and get strTypeLlave correctly")
    void shouldSetAndGetStrTypeLlaveCorrectly() {
        KeyEntity keyEntity = new KeyEntity();
        keyEntity.setStrTypeLlave("TypeA");

        assertEquals("TypeA", keyEntity.getStrTypeLlave());
    }
}