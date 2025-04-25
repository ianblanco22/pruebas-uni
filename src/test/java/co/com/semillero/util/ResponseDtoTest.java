package co.com.semillero.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ResponseDtoTest {

    @Test
    @DisplayName("ReturnsCorrectStatusCodeWhenSet")
    void returnsCorrectStatusCodeWhenSet() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode("200");
        assertEquals("200", responseDto.getStatusCode());
    }

    @Test
    @DisplayName("ReturnsCorrectMessageWhenSet")
    void returnsCorrectMessageWhenSet() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Success");
        assertEquals("Success", responseDto.getMessage());
    }

    @Test
    @DisplayName("ReturnsCorrectErrorDetailWhenSet")
    void returnsCorrectErrorDetailWhenSet() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setErrorDetail(Collections.singletonList("Error detail"));
        assertEquals(Collections.singletonList("Error detail"), responseDto.getErrorDetail());
    }

    @Test
    @DisplayName("ReturnsCorrectResultWhenSet")
    void returnsCorrectResultWhenSet() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResult("Result");
        assertEquals("Result", responseDto.getResult());
    }

    @Test
    @DisplayName("HandlesNullValuesGracefully")
    void handlesNullValuesGracefully() {
        ResponseDto responseDto = new ResponseDto();
        assertNull(responseDto.getStatusCode());
        assertNull(responseDto.getMessage());
        assertNull(responseDto.getErrorDetail());
        assertNull(responseDto.getResult());
    }
}