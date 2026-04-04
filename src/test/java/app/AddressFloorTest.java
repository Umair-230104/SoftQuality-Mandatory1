package app;

import app.util.AddressValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class AddressFloorTest {

    // -------- VALID --------

    @ParameterizedTest
    @ValueSource(strings = {"st", "1", "51", "99"})
    @DisplayName("Valid v1–v2: 'st' or 1–99")
    void valid_floor(String floor) {
        assertDoesNotThrow(() -> AddressValidator.validateFloor(floor));
    }

    // -------- INVALID --------

    @ParameterizedTest
    @NullSource
    @DisplayName("Invalid i1: null")
    void invalid_null(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateFloor(floor)
        );
        assertEquals("Floor must not be null", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Invalid i2: empty")
    void invalid_empty(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateFloor(floor)
        );
        assertEquals("Floor must not be empty", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123"})
    @DisplayName("Invalid i3: length > 2")
    void invalid_length(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateFloor(floor)
        );
        assertEquals("Floor has invalid length", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ST"})
    @DisplayName("Invalid i4: uppercase")
    void invalid_uppercase(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateFloor(floor)
        );
        assertEquals("Floor must be \"st\" or 1–99", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"s@"})
    @DisplayName("Invalid i5: special char")
    void invalid_special(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateFloor(floor)
        );
        assertEquals("Floor must be \"st\" or 1–99", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AB", "ab"})
    @DisplayName("Invalid i6: invalid letters")
    void invalid_letters(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateFloor(floor)
        );
        assertEquals("Floor must be \"st\" or 1–99", ex.getMessage());
    }
}