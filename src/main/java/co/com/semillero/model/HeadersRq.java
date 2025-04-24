package co.com.semillero.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(HeadersRq.class)
public class HeadersRq {

    @JsonProperty("Content-Length")
    private String contentLength;
    @JsonProperty("Content-Type")
    private String contentType;
    @JsonProperty("Accept")
    private String accept;
    
}
