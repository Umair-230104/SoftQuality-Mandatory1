package app;

import app.util.AddressValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class AddressNumberTest {

    // -------- VALID --------

    @ParameterizedTest
    @ValueSource(strings = {"1", "502", "43B"})
    @DisplayName("Valid v1–v2")
    void valid_number(String number) {
        assertDoesNotThrow(() -> AddressValidator.validateNumber(number));
    }

    // -------- INVALID --------

    @ParameterizedTest
    @NullSource
    void invalid_null(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Number must not be null", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    void invalid_empty(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Number must not be empty", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "12345"})
    void invalid_length(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Number has too many digits", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"43b"})
    void invalid_lowercase(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Suffix letter must be uppercase", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"3B4"})
    void invalid_middle_letter(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Number must have at most one suffix letter", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"B43"})
    void invalid_start_letter(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Number must start with a digit", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"34AB"})
    void invalid_multiple_letters(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Number must have at most one suffix letter", ex.getMessage());
    }


    // NOTE:
// This test previously expected certain uppercase letters (e.g., I, J, O, Q) to be invalid.
// However, according to the assignment, any uppercase letter is allowed as suffix.
//
// The test has therefore been updated to reflect that all uppercase suffix letters are valid.
    @ParameterizedTest
    @ValueSource(strings = {"43I", "43J", "43O", "43Q"})
    void valid_suffix_letters(String number) {
        assertDoesNotThrow(() -> AddressValidator.validateNumber(number));
    }


    @ParameterizedTest
    @ValueSource(strings = {"43%"})
    void invalid_special(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressValidator.validateNumber(number)
        );
        assertEquals("Number contains a special character", ex.getMessage());
    }
}