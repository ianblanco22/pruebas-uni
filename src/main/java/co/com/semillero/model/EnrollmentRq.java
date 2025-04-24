package co.com.semillero.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Introspected
@SerdeImport(EnrollmentRq.class)
public class EnrollmentRq {
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty ("apellido")
    private String apellido;
    @JsonProperty ("numeroDocumento")
    private String numeroDocumento;
    @JsonProperty ("tipoDocumento")
    private String tipoDocumento;

}
