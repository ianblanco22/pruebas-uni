package co.com.semillero.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import static org.junit.jupiter.api.Assertions.*;

class ClientEntityTest {

    @Test
    @DisplayName("Should correctly map attributes to schema")
    void shouldCorrectlyMapAttributesToSchema() {
        TableSchema<ClientEntity> schema = ClientEntity.SCHEMA_CLIENT;

        assertNotNull(schema);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId("123");
        clientEntity.setSk("456");
        clientEntity.setStrFirstName("John");
        clientEntity.setStrLastName("Doe");

        assertEquals("123", clientEntity.getId());
        assertEquals("456", clientEntity.getSk());
        assertEquals("John", clientEntity.getStrFirstName());
        assertEquals("Doe", clientEntity.getStrLastName());
    }

    @Test
    @DisplayName("Should create new ClientEntity instance using schema")
    void shouldCreateNewClientEntityInstanceUsingSchema() {
        ClientEntity clientEntity = new ClientEntity();

        assertNotNull(clientEntity);
        assertTrue(clientEntity instanceof ClientEntity);
    }

    @Test
    @DisplayName("Should set and get id correctly")
    void shouldSetAndGetIdCorrectly() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId("123");

        assertEquals("123", clientEntity.getId());
    }

    @Test
    @DisplayName("Should set and get sk correctly")
    void shouldSetAndGetSkCorrectly() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setSk("456");

        assertEquals("456", clientEntity.getSk());
    }

    @Test
    @DisplayName("Should set and get strFirstName correctly")
    void shouldSetAndGetStrFirstNameCorrectly() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setStrFirstName("John");

        assertEquals("John", clientEntity.getStrFirstName());
    }

    @Test
    @DisplayName("Should set and get strLastName correctly")
    void shouldSetAndGetStrLastNameCorrectly() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setStrLastName("Doe");

        assertEquals("Doe", clientEntity.getStrLastName());
    }

    @Test
    @DisplayName("Should set and get account correctly")
    void shouldSetAndGetAccountCorrectly() {
        ClientEntity clientEntity = new ClientEntity();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setStrAccount("789");
        clientEntity.setAccount(accountEntity);

        assertNotNull(clientEntity.getAccount());
        assertEquals("789", clientEntity.getAccount().getStrAccount());
    }
}