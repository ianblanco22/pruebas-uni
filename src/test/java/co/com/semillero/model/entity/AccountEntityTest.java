package co.com.semillero.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import static org.junit.jupiter.api.Assertions.*;

class AccountEntityTest {

    @Test
    @DisplayName("Should correctly map attributes to schema")
    void shouldCorrectlyMapAttributesToSchema() {
        TableSchema<AccountEntity> schema = AccountEntity.SCHEMA;

        assertNotNull(schema);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setStrAccount("12345");
        accountEntity.setStrBank("TestBank");

        assertEquals("12345", accountEntity.getStrAccount());
        assertEquals("TestBank", accountEntity.getStrBank());
    }

    @Test
    @DisplayName("Should create new AccountEntity instance using schema")
    void shouldCreateNewAccountEntityInstanceUsingSchema() {
        AccountEntity accountEntity = new AccountEntity();

        assertNotNull(accountEntity);
        assertTrue(accountEntity instanceof AccountEntity);
    }

    @Test
    @DisplayName("Should set and get strAccount correctly")
    void shouldSetAndGetStrAccountCorrectly() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setStrAccount("12345");

        assertEquals("12345", accountEntity.getStrAccount());
    }

    @Test
    @DisplayName("Should set and get strBank correctly")
    void shouldSetAndGetStrBankCorrectly() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setStrBank("TestBank");

        assertEquals("TestBank", accountEntity.getStrBank());
    }
}