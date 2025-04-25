package co.com.semillero.mapper;

import co.com.semillero.model.Client;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.model.entity.AccountEntity;
import co.com.semillero.model.entity.KeyEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    @Test
    void clientMapperMapsClientToClientEntityCorrectly() {
        ClientMapper clientMapper = new ClientMapper();
        Client client = new Client();
        client.setStrFirstName("John");
        client.setStrLastName("Doe");
        client.setStrEmail("john.doe@example.com");
        client.setStrPhoneNumber("123456789");
        client.setStrIdentificationType("ID");
        client.setStrIdentificationNum("12345");
        client.setStrAddress("123 Main St");

        AccountEntity account = new AccountEntity();
        account.setStrAccount("123456");
        account.setStrBank("BankName");
        client.setAccount(account);

        KeyEntity key = new KeyEntity();
        key.setStrIdLlave("key123");
        key.setStrTypeLlave("typeA");
        client.setKey(key);

        ClientEntity result = clientMapper.clientMapper(client);

        assertNotNull(result);
        assertEquals("John", result.getStrFirstName());
        assertEquals("Doe", result.getStrLastName());
        assertEquals("john.doe@example.com", result.getStrEmail());
        assertEquals("123456789", result.getStrPhoneNumber());
        assertEquals("ID", result.getStrIdentificationType());
        assertEquals("12345", result.getStrIdentificationNum());
        assertEquals("123 Main St", result.getStrAddress());
        assertNotNull(result.getAccount());
        assertEquals("123456", result.getAccount().getStrAccount());
        assertEquals("BankName", result.getAccount().getStrBank());
        assertNotNull(result.getKey());
        assertEquals("key123", result.getKey().getStrIdLlave());
        assertEquals("typeA", result.getKey().getStrTypeLlave());
    }

    @Test
    void clientMapperHandlesNullClientGracefully() {
        try {
            ClientMapper clientMapper = new ClientMapper();
            ClientEntity result = clientMapper.clientMapper(null);
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException e) {
            assertEquals("Client cannot be null", e.getMessage());
        }
    }

    @Test
    void clientMapperHandlesNullFieldsInClient() {
        ClientMapper clientMapper = new ClientMapper();
        Client client = new Client();
        try {
            ClientEntity result = clientMapper.clientMapper(client);

            assertNotNull(result);
            assertNull(result.getStrFirstName());
            assertNull(result.getStrLastName());
            assertNull(result.getStrEmail());
            assertNull(result.getStrPhoneNumber());
            assertNull(result.getStrIdentificationType());
            assertNull(result.getStrIdentificationNum());
            assertNull(result.getStrAddress());
            assertNull(result.getAccount());
            assertNull(result.getKey());
        } catch (NullPointerException e) {
            assertEquals("Client cannot be null", e.getMessage());
        }
    }
}