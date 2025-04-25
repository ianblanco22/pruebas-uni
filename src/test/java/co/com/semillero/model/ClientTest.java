package co.com.semillero.model;

import co.com.semillero.model.entity.AccountEntity;
import co.com.semillero.model.entity.KeyEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    @DisplayName("Should set and get numID correctly")
    void shouldSetAndGetNumIDCorrectly() {
        Client client = new Client();
        client.setNumID(123);

        assertEquals(123, client.getNumID());
    }

    @Test
    @DisplayName("Should set and get strIdentificationType correctly")
    void shouldSetAndGetStrIdentificationTypeCorrectly() {
        Client client = new Client();
        client.setStrIdentificationType("Passport");

        assertEquals("Passport", client.getStrIdentificationType());
    }

    @Test
    @DisplayName("Should set and get strIdentificationNum correctly")
    void shouldSetAndGetStrIdentificationNumCorrectly() {
        Client client = new Client();
        client.setStrIdentificationNum("A12345678");

        assertEquals("A12345678", client.getStrIdentificationNum());
    }

    @Test
    @DisplayName("Should set and get strFirstName correctly")
    void shouldSetAndGetStrFirstNameCorrectly() {
        Client client = new Client();
        client.setStrFirstName("John");

        assertEquals("John", client.getStrFirstName());
    }

    @Test
    @DisplayName("Should set and get strLastName correctly")
    void shouldSetAndGetStrLastNameCorrectly() {
        Client client = new Client();
        client.setStrLastName("Doe");

        assertEquals("Doe", client.getStrLastName());
    }

    @Test
    @DisplayName("Should set and get strAddress correctly")
    void shouldSetAndGetStrAddressCorrectly() {
        Client client = new Client();
        client.setStrAddress("123 Main St");

        assertEquals("123 Main St", client.getStrAddress());
    }

    @Test
    @DisplayName("Should set and get strPhoneNumber correctly")
    void shouldSetAndGetStrPhoneNumberCorrectly() {
        Client client = new Client();
        client.setStrPhoneNumber("555-1234");

        assertEquals("555-1234", client.getStrPhoneNumber());
    }

    @Test
    @DisplayName("Should set and get strEmail correctly")
    void shouldSetAndGetStrEmailCorrectly() {
        Client client = new Client();
        client.setStrEmail("john.doe@example.com");

        assertEquals("john.doe@example.com", client.getStrEmail());
    }

    @Test
    @DisplayName("Should set and get key correctly")
    void shouldSetAndGetKeyCorrectly() {
        Client client = new Client();
        KeyEntity keyEntity = new KeyEntity();
        keyEntity.setStrIdLlave("Key123");
        client.setKey(keyEntity);

        assertNotNull(client.getKey());
        assertEquals("Key123", client.getKey().getStrIdLlave());
    }

    @Test
    @DisplayName("Should set and get account correctly")
    void shouldSetAndGetAccountCorrectly() {
        Client client = new Client();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setStrAccount("Acc456");
        client.setAccount(accountEntity);

        assertNotNull(client.getAccount());
        assertEquals("Acc456", client.getAccount().getStrAccount());
    }
}