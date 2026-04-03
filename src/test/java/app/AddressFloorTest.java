package app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// ADDRESS FLOOR tests.
class AddressFloorTest {

    // -------------------------
    // Positive tests — valid v1–v2 (totals 1–2)
    // -------------------------

    @ParameterizedTest
    @CsvSource({
            "st",
            "51",
    })
    @DisplayName("Floor totals 1–2 — valid v1, v2")
    void valid_totals1and2(String floor) {
        assertDoesNotThrow(() ->
                AddressFloorTest.Spec.validate(floor));
    }

    // -------------------------
    // Negative tests — invalid i1–i6 (totals 3–7, i4)
    // -------------------------

    @ParameterizedTest
    @NullSource
    @DisplayName("Floor total 3 — invalid i1 null")
    void invalid_total3_null(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressFloorTest.Spec.validate(floor)
        );

        assertEquals("Floor must not be null", ex.getMessage());
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Floor total 4 — invalid i2 empty")
    void invalid_total4_empty(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressFloorTest.Spec.validate(floor)
        );

        assertEquals("Floor must not be empty", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"123"})
    @DisplayName("Floor total 5 — invalid i3")
    void invalid_total5(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressFloorTest.Spec.validate(floor)
        );

        assertEquals("Floor has invalid length", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"s@"})
    @DisplayName("Floor total 6 — invalid i5")
    void invalid_total6(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressFloorTest.Spec.validate(floor)
        );

        assertEquals("Floor must be \"st\" or 1–99", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"ST"})
    @DisplayName("Floor — invalid i4 uppercase ST")
    void invalid_i4_uppercaseSt(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressFloorTest.Spec.validate(floor)
        );

        assertEquals("Floor must be \"st\" or 1–99", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AB", "ab"})
    @DisplayName("Floor total 7 — invalid i6")
    void invalid_total7(String floor) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressFloorTest.Spec.validate(floor)
        );

        assertEquals("Floor must be \"st\" or 1–99", ex.getMessage());
    }

    static final class Spec {

        private Spec() {
        }

        static void validate(String floor) {
            if (floor == null) {
                throw new IllegalArgumentException("Floor must not be null");
            }
            if (floor.isEmpty()) {
                throw new IllegalArgumentException("Floor must not be empty");
            }
            if (floor.length() > 2) {
                throw new IllegalArgumentException("Floor has invalid length");
            }
            if ("st".equals(floor)) {
                return;
            }
            if (!floor.matches("[1-9]\\d?")) {
                throw new IllegalArgumentException("Floor must be " + "\"st\"" + " or 1–99");
            }
            int value = Integer.parseInt(floor);
            if (value < 1 || value > 99) {
                throw new IllegalArgumentException("Floor out of range");
            }
        }
    }
}
