package co.com.semillero.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentRqTest {

    @Test
    @DisplayName("Should set and get nombre correctly")
    void shouldSetAndGetNombreCorrectly() {
        EnrollmentRq enrollmentRq = new EnrollmentRq();
        enrollmentRq.setNombre("Carlos");

        assertEquals("Carlos", enrollmentRq.getNombre());
    }

    @Test
    @DisplayName("Should set and get apellido correctly")
    void shouldSetAndGetApellidoCorrectly() {
        EnrollmentRq enrollmentRq = new EnrollmentRq();
        enrollmentRq.setApellido("Gomez");

        assertEquals("Gomez", enrollmentRq.getApellido());
    }

    @Test
    @DisplayName("Should set and get numeroDocumento correctly")
    void shouldSetAndGetNumeroDocumentoCorrectly() {
        EnrollmentRq enrollmentRq = new EnrollmentRq();
        enrollmentRq.setNumeroDocumento("123456789");

        assertEquals("123456789", enrollmentRq.getNumeroDocumento());
    }

    @Test
    @DisplayName("Should set and get tipoDocumento correctly")
    void shouldSetAndGetTipoDocumentoCorrectly() {
        EnrollmentRq enrollmentRq = new EnrollmentRq();
        enrollmentRq.setTipoDocumento("CC");

        assertEquals("CC", enrollmentRq.getTipoDocumento());
    }

    @Test
    @DisplayName("Should handle null values for all fields")
    void shouldHandleNullValuesForAllFields() {
        EnrollmentRq enrollmentRq = new EnrollmentRq();
        enrollmentRq.setNombre(null);
        enrollmentRq.setApellido(null);
        enrollmentRq.setNumeroDocumento(null);
        enrollmentRq.setTipoDocumento(null);

        assertNull(enrollmentRq.getNombre());
        assertNull(enrollmentRq.getApellido());
        assertNull(enrollmentRq.getNumeroDocumento());
        assertNull(enrollmentRq.getTipoDocumento());
    }
}