package app;

import app.util.AddressValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AddressDoorTest {

    @ParameterizedTest
    @ValueSource(strings = {"th", "mf", "tv"})
    void valid_fixed(String door) {
        assertDoesNotThrow(() -> AddressValidator.validateDoor(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "25", "49", "50"})
    void valid_numbers(String door) {
        assertDoesNotThrow(() -> AddressValidator.validateDoor(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"c1", "c999", "z999"})
    void valid_letter_digits(String door) {
        assertDoesNotThrow(() -> AddressValidator.validateDoor(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"d-14", "b-1", "x-250"})
    void valid_hyphen(String door) {
        assertDoesNotThrow(() -> AddressValidator.validateDoor(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"tt", "abc"})
    void invalid_strings(String door) {
        assertThrows(IllegalArgumentException.class,
                () -> AddressValidator.validateDoor(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "115", "-25", "51"})
    void invalid_numbers(String door) {
        assertThrows(IllegalArgumentException.class,
                () -> AddressValidator.validateDoor(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"D-14"})
    void invalid_uppercase(String door) {
        assertThrows(IllegalArgumentException.class,
                () -> AddressValidator.validateDoor(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"d-"})
    void invalid_missing_digits(String door) {
        assertThrows(IllegalArgumentException.class,
                () -> AddressValidator.validateDoor(door));
    }
}