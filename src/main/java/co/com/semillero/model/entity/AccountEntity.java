package co.com.semillero.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Getter
@Setter
@Introspected
@SerdeImport(AccountEntity.class)
@DynamoDBDocument
public class AccountEntity {
    @DynamoDBAttribute()
    protected String strAccount;
    @DynamoDBAttribute()
    protected String strBank;

    public static final TableSchema<AccountEntity> SCHEMA=
            TableSchema.builder(AccountEntity.class)
                    .newItemSupplier(AccountEntity::new)
                    .addAttribute(String.class, a -> a.name("strAccount")
                            .getter(AccountEntity::getStrAccount)
                            .setter(AccountEntity::setStrAccount))
                    .addAttribute(String.class, a -> a.name("strBank")
                            .getter(AccountEntity::getStrBank)
                            .setter(AccountEntity::setStrAccount))
                    .build();
}
