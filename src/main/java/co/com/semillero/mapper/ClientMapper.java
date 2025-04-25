package co.com.semillero.mapper;

import co.com.semillero.exception.ErrorResponse;
import co.com.semillero.model.Client;
import co.com.semillero.model.entity.AccountEntity;
import co.com.semillero.model.entity.ClientEntity;
import co.com.semillero.model.entity.KeyEntity;
import co.com.semillero.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ClientMapper {

    //private static final Logger logger = LoggerFactory.getLogger(ClientMapper.class);

    public ClientEntity clientMapper(Client client) {

        ClientEntity clientEntity = new ClientEntity();
        AccountEntity accountEntity = new AccountEntity();
        KeyEntity keyEntity = new KeyEntity();

        try {
            log.info("Client: {}", Util.object2String(client));

            log.info("Mapping Client to ClientEntity...");

            // Log de AccountEntity
            log.info("AccountEntity - strAccount: {}", client.getAccount().getStrAccount());
            log.info("AccountEntity - strBank: {}", client.getAccount().getStrBank());
            accountEntity.setStrAccount(client.getAccount().getStrAccount());
            accountEntity.setStrBank(client.getAccount().getStrBank());

            // Log de KeyEntity
            log.info("KeyEntity - strIdLlave: {}", client.getKey().getStrIdLlave());
            log.info("KeyEntity - strTypeLlave: {}", client.getKey().getStrTypeLlave());
            keyEntity.setStrIdLlave(client.getKey().getStrIdLlave());
            keyEntity.setStrTypeLlave(client.getKey().getStrTypeLlave());

            // Log de ClientEntity
            String id = client.getKey().getStrTypeLlave() + "_" + keyEntity.getStrIdLlave();
            log.info("ClientEntity - id: {}", id);
            clientEntity.setId(id);
            clientEntity.setSk(id);
            log.info("ClientEntity - strFirstName: {}", client.getStrFirstName());
            log.info("ClientEntity - strLastName: {}", client.getStrLastName());
            log.info("ClientEntity - strEmail: {}", client.getStrEmail());
            log.info("ClientEntity - strPhoneNumber: {}", client.getStrPhoneNumber());
            log.info("ClientEntity - strIdentificationType: {}", client.getStrIdentificationType());
            log.info("ClientEntity - strIdentificationNum: {}", client.getStrIdentificationNum());
            log.info("ClientEntity - strAddress: {}", client.getStrAddress());

            clientEntity.setStrFirstName(client.getStrFirstName());
            clientEntity.setStrLastName(client.getStrLastName());
            clientEntity.setStrEmail(client.getStrEmail());
            clientEntity.setStrPhoneNumber(client.getStrPhoneNumber());
            clientEntity.setStrIdentificationType(client.getStrIdentificationType());
            clientEntity.setStrIdentificationNum(client.getStrIdentificationNum());
            clientEntity.setStrAddress(client.getStrAddress());

            clientEntity.setAccount(accountEntity);
            clientEntity.setKey(keyEntity);

            log.info("ClientEntity: {}", Util.object2String(clientEntity));
            log.info("ClientEntity mapping completed.");

            return clientEntity;
        } catch (NullPointerException e) {
            throw new NullPointerException("Client cannot be null");
        }
    }
}
