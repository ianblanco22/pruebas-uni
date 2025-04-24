package co.com.semillero.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;

@Getter
@Setter
@Introspected
@SerdeImport(ClientEntity.class)
@DynamoDBDocument
public class ClientEntity {

    @DynamoDBHashKey(attributeName = "id")
    protected String id;

    @DynamoDBRangeKey(attributeName = "sk")
    protected String sk;

    @DynamoDBAttribute(attributeName = "key")
    protected KeyEntity key;

    @DynamoDBAttribute(attributeName = "numID")
    protected int numID;

    @DynamoDBAttribute(attributeName = "strIdentificationType")
    protected String strIdentificationType;

    @DynamoDBAttribute(attributeName = "strIdentificationNum")
    protected String strIdentificationNum;

    @DynamoDBAttribute(attributeName = "strFirstName")
    protected String strFirstName;

    @DynamoDBAttribute(attributeName = "strLastName")
    protected String strLastName;

    @DynamoDBAttribute(attributeName = "strAddress")
    protected String strAddress;

    @DynamoDBAttribute(attributeName = "strPhoneNumber")
    protected String strPhoneNumber;

    @DynamoDBAttribute(attributeName = "strEmail")
    protected String strEmail;

    @DynamoDBAttribute(attributeName = "account")
    protected AccountEntity account;

    public static final TableSchema<ClientEntity> SCHEMA_CLIENT =
            TableSchema.builder(ClientEntity.class)
                    .newItemSupplier(ClientEntity::new)
                    .addAttribute(String.class, a -> a.name("id")
                            .getter(ClientEntity::getId)
                            .setter(ClientEntity::setId)
                            .tags(StaticAttributeTags.primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("sk")
                            .getter(ClientEntity::getSk)
                            .setter(ClientEntity::setSk)
                            .tags(StaticAttributeTags.primarySortKey()))
                    .addAttribute(
                            EnhancedType.documentOf(KeyEntity.class, KeyEntity.SCHEMA),
                            a -> a.name("key")
                                    .getter(ClientEntity::getKey)
                                    .setter(ClientEntity::setKey))
                    .addAttribute(Integer.class, a -> a.name("numID")
                            .getter(ClientEntity::getNumID)
                            .setter(ClientEntity::setNumID))
                    .addAttribute(String.class, a -> a.name("strIdentificationType")
                            .getter(ClientEntity::getStrIdentificationType)
                            .setter(ClientEntity::setStrIdentificationType))
                    .addAttribute(String.class, a -> a.name("strIdentificationNum")
                            .getter(ClientEntity::getStrIdentificationNum)
                            .setter(ClientEntity::setStrIdentificationNum))
                    .addAttribute(String.class, a -> a.name("strFirstName")
                            .getter(ClientEntity::getStrFirstName)
                            .setter(ClientEntity::setStrFirstName))
                    .addAttribute(String.class, a -> a.name("strLastName")
                            .getter(ClientEntity::getStrLastName)
                            .setter(ClientEntity::setStrLastName))
                    .addAttribute(String.class, a -> a.name("strAddress")
                            .getter(ClientEntity::getStrAddress)
                            .setter(ClientEntity::setStrAddress))
                    .addAttribute(String.class, a -> a.name("strPhoneNumber")
                            .getter(ClientEntity::getStrPhoneNumber)
                            .setter(ClientEntity::setStrPhoneNumber))
                    .addAttribute(String.class, a -> a.name("strEmail")
                            .getter(ClientEntity::getStrEmail)
                            .setter(ClientEntity::setStrEmail))
                    .addAttribute(
                            EnhancedType.documentOf(AccountEntity.class, AccountEntity.SCHEMA),
                            a -> a.name("account")
                                    .getter(ClientEntity::getAccount)
                                    .setter(ClientEntity::setAccount))
                    .build();

}
