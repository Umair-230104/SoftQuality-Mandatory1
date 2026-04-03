package app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// ADDRESS NUMBER tests.
class AddressNumberTest {

    // -------------------------
    // Positive tests — valid v1–v2 (totals 1–2)
    // -------------------------

    @ParameterizedTest
    @CsvSource({
            "502",
            "43B",
    })
    @DisplayName("Number totals 1–2 — valid v1, v2")
    void valid_totals1and2(String number) {
        assertDoesNotThrow(() ->
                AddressNumberTest.Spec.validate(number));
    }

    // -------------------------
    // Negative tests — invalid i1–i10 (totals 3–12)
    // -------------------------

    @ParameterizedTest
    @NullSource
    @DisplayName("Number total 3 — invalid i1 null")
    void invalid_total3_null(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressNumberTest.Spec.validate(number)
        );

        assertEquals("Number must not be null", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Number total 4 — invalid i2 empty")
    void invalid_total4_empty(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressNumberTest.Spec.validate(number)
        );

        assertEquals("Number must not be empty", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "12345"})
    @DisplayName("Number totals 5–6 — invalid i3")
    void invalid_totals5and6(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressNumberTest.Spec.validate(number)
        );

        assertEquals("Number has too many digits", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "43b|Suffix letter must be uppercase",
            "3B4|Number must have at most one suffix letter",
            "B43|Number must start with a digit",
            "34AB|Number must have at most one suffix letter",
    })
    @DisplayName("Number totals 7–10 — invalid i5–i8")
    void invalid_totals7through10(String number, String expectedMessage) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressNumberTest.Spec.validate(number)
        );

        assertEquals(expectedMessage, ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"43I", "43J", "43O", "43Q"})
    @DisplayName("Number total 11 — invalid i9")
    void invalid_total11(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressNumberTest.Spec.validate(number)
        );

        assertEquals("Suffix letter is not allowed", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"43%"})
    @DisplayName("Number total 12 — invalid i10 special character")
    void invalid_total12(String number) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressNumberTest.Spec.validate(number)
        );

        assertEquals("Number contains a special character", ex.getMessage());
    }

    static final class Spec {

        private static final String HOUSE_NUMBER_SUFFIX = "ABCDEFGHKLMNPRSTUVWXYZ";

        private Spec() {
        }

        static void validate(String number) {
            if (number == null) {
                throw new IllegalArgumentException("Number must not be null");
            }
            if (number.isEmpty()) {
                throw new IllegalArgumentException("Number must not be empty");
            }
            int i = 0;
            while (i < number.length() && Character.isDigit(number.charAt(i))) {
                i++;
            }
            if (i == 0) {
                throw new IllegalArgumentException("Number must start with a digit");
            }
            String digitPart = number.substring(0, i);
            if (digitPart.length() > 3) {
                throw new IllegalArgumentException("Number has too many digits");
            }
            int numeric = Integer.parseInt(digitPart);
            if (numeric < 1 || numeric > 999) {
                throw new IllegalArgumentException("Number out of range");
            }
            if (i == number.length()) {
                return;
            }
            if (number.length() - i != 1) {
                throw new IllegalArgumentException("Number must have at most one suffix letter");
            }
            char suffix = number.charAt(i);
            if (!Character.isLetter(suffix)) {
                throw new IllegalArgumentException("Number contains a special character");
            }
            if (!Character.isUpperCase(suffix)) {
                throw new IllegalArgumentException("Suffix letter must be uppercase");
            }
            if (HOUSE_NUMBER_SUFFIX.indexOf(suffix) < 0) {
                throw new IllegalArgumentException("Suffix letter is not allowed");
            }
        }
    }
}
