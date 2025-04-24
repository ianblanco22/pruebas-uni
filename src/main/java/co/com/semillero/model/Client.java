package co.com.semillero.model;

import co.com.semillero.model.entity.AccountEntity;
import co.com.semillero.model.entity.KeyEntity;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(Client.class)
public class Client {

    protected int numID;
    protected String strIdentificationType;
    protected String strIdentificationNum;
    protected String strFirstName;
    protected String strLastName;
    protected String strAddress;
    protected String strPhoneNumber;
    protected String strEmail;
    protected KeyEntity key;
    protected AccountEntity account;

}
