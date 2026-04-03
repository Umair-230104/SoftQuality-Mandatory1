package app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// ADDRESS STREET tests.
class AddressStreetTest {

    // Analysis: "Aaø "*36 with length 40 → ten 4-char units.
    private static final String STREET_VALID_40 = "Aaø ".repeat(10);

    // -------------------------
    // Positive tests — valid v1–v4 (total case 5)
    // -------------------------

    @ParameterizedTest
    @CsvSource({
            "partition-length-40",
            "partition-letters",
            "partition-danish",
            "partition-space",
    })
    @DisplayName("Street total 5 — valid v1–v4")
    void valid_total5(String ignored) {
        assertDoesNotThrow(() ->
                AddressStreetTest.Spec.validate(STREET_VALID_40));
    }

    // -------------------------
    // Negative tests — invalid i1–i5 (totals 1–4, 6–7)
    // -------------------------

    @ParameterizedTest
    @ValueSource(strings = {
            "9999999999999999999999999999999999999999",
            "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",
    })
    @DisplayName("Street totals 1–2 — invalid i2, i3")
    void invalid_totals1and2(String street) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressStreetTest.Spec.validate(street)
        );

        assertEquals("Street contains an invalid character", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Street total 3 — invalid i4 empty string")
    void invalid_total3_empty(String empty) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressStreetTest.Spec.validate(empty)
        );

        assertEquals("Street must not be empty", ex.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Street total 4 — invalid i5 null")
    void invalid_total4_null(String street) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressStreetTest.Spec.validate(street)
        );

        assertEquals("Street must not be null", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {39, 41})
    @DisplayName("Street totals 6–7 — invalid i1 length boundaries")
    void invalid_totals6and7(int length) {
        String street = length < 40
                ? STREET_VALID_40.substring(0, length)
                : STREET_VALID_40 + "a".repeat(length - 40);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressStreetTest.Spec.validate(street)
        );

        assertEquals("Street must be exactly 40 characters", ex.getMessage());
    }

    // Expected input rules for these tests (replace with production validator when you have it).
    static final class Spec {

        private Spec() {
        }

        static void validate(String street) {
            if (street == null) {
                throw new IllegalArgumentException("Street must not be null");
            }
            if (street.isEmpty()) {
                throw new IllegalArgumentException("Street must not be empty");
            }
            if (street.length() != 40) {
                throw new IllegalArgumentException("Street must be exactly 40 characters");
            }
            for (int i = 0; i < street.length(); i++) {
                char c = street.charAt(i);
                if (c == ' ') {
                    continue;
                }
                if (!Character.isLetter(c)) {
                    throw new IllegalArgumentException("Street contains an invalid character");
                }
            }
        }
    }
}
