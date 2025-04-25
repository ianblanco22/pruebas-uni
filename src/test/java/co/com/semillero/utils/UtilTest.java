package co.com.semillero.utils;
import co.com.semillero.util.Util;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class UtilTest {
    @Test
    @DisplayName("string2object throws RuntimeException for invalid JSON")
    void string2objectThrowsRuntimeExceptionForInvalidJson() {
        String invalidJson = "{invalidJson}";

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                Util.string2object(invalidJson, Object.class)
        );

        assertEquals(IOException.class, exception.getCause().getClass());
    }

    @Test
    @DisplayName("string2object returns null for empty JSON")
    void string2objectReturnsNullForEmptyJson() {
        String emptyJson = "";

        Object result = Util.string2object(emptyJson, Object.class);

        assertNull(result);
    }

    @Test
    @DisplayName("object2String throws RuntimeException for non-serializable object")
    void object2StringThrowsRuntimeExceptionForNonSerializableObject() {
        Object nonSerializableObject = new Object() {
            private final Object circularReference = this;
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                Util.object2String(nonSerializableObject)
        );

        assertEquals(IOException.class, exception.getCause().getClass());
    }

    @Test
    @DisplayName("stringUTF8 returns UTF-8 encoded string")
    void stringUTF8ReturnsUtf8EncodedString() {
        String input = "áéíóú";

        String result = Util.stringUTF8(input);

        assertEquals(input, result);
    }

    @Test
    @DisplayName("createDate returns formatted date with default pattern")
    void createDateReturnsFormattedDateWithDefaultPattern() {
        String result = Util.createDate();

        assertTrue(result.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("createDate returns formatted date with custom pattern")
    void createDateReturnsFormattedDateWithCustomPattern() {
        String pattern = "yyyy/MM/dd HH:mm:ss";
        String result = Util.createDate(pattern);

        assertTrue(result.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    @DisplayName("convertToJson throws IOException for invalid AttributeValue map")
    void convertToJsonThrowsIOExceptionForInvalidAttributeValueMap() {
        Map<String, AttributeValue> invalidMap = new HashMap<>();
        invalidMap.put("key", null);

        assertThrows(IOException.class, () -> Util.convertToJson(invalidMap));
    }
}
