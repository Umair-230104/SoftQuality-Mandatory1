package app;

import app.util.AddressValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class AddressStreetTest {

    private static final String BASE = "Aaø";

    // -------- VALID BOUNDARY VALUES --------

    @ParameterizedTest
    @ValueSource(ints = {1, 39, 40}) // min, near-max, max
    @DisplayName("Street lengths within valid boundaries (1–40 chars)")
    void valid_street_lengths(int length) {
        String street = BASE.repeat((length + 2) / 3).substring(0, length); // generate length
        assertDoesNotThrow(() -> AddressValidator.validateStreet(street));
    }

    // -------- INVALID BOUNDARY VALUE (too long) --------

    @ParameterizedTest
    @ValueSource(ints = {41}) // just above max
    @DisplayName("Street length exceeding 40 characters is invalid")
    void invalid_too_long(int length) {
        String street = BASE.repeat((length + 2) / 3).substring(0, length);
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateStreet(street)
        );
        assertEquals("Street must be at most 40 characters", ex.getMessage());
    }

    // -------- INVALID CHARACTERS --------

    @ParameterizedTest
    @ValueSource(strings = {
            "9999999999999999999999999999999999999999",
            "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
    })
    @DisplayName("Street with invalid characters is invalid")
    void invalid_chars(String street) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateStreet(street)
        );
        assertEquals("Street contains an invalid character", ex.getMessage());
    }

    // -------- EMPTY --------

    @ParameterizedTest
    @EmptySource
    @DisplayName("Street cannot be empty")
    void invalid_empty(String street) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateStreet(street)
        );
        assertEquals("Street must not be empty", ex.getMessage());
    }

    // -------- NULL --------

    @ParameterizedTest
    @NullSource
    @DisplayName("Street cannot be null")
    void invalid_null(String street) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateStreet(street)
        );
        assertEquals("Street must not be null", ex.getMessage());
    }
}