package co.com.semillero.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(ParameterStoreDTO.class)
public class ParameterStoreDTO {
    String urlDynamo;
    String region;
    String arnSecre;
    String table;
}
