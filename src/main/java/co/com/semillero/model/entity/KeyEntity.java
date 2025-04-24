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
@SerdeImport(KeyEntity.class)
@DynamoDBDocument
public class KeyEntity {
    @DynamoDBAttribute()
    protected String strIdLlave;
    @DynamoDBAttribute()
    protected String strTypeLlave;

    public static final TableSchema<KeyEntity> SCHEMA=
            TableSchema.builder(KeyEntity.class)
                    .newItemSupplier(KeyEntity::new)
                    .addAttribute(String.class, a ->a.name("strIdLlave")
                            .getter(KeyEntity::getStrIdLlave)
                            .setter(KeyEntity::setStrIdLlave))
                    .addAttribute(String.class, a -> a.name("strTypeLlave")
                            .getter(KeyEntity::getStrTypeLlave)
                            .setter(KeyEntity::setStrTypeLlave))
                    .build();
}
