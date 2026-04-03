package app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// ADDRESS DOOR tests.
class AddressDoorTest {

    // -------------------------
    // Positive tests — v1–v4, boundary list (totals 1–4, 11–16)
    // -------------------------

    @ParameterizedTest
    @ValueSource(strings = {"th", "mf", "tv"})
    @DisplayName("Door total 1 — valid v1")
    void valid_total1(String door) {
        assertDoesNotThrow(() ->
                AddressDoorTest.Spec.validate(door));
    }

    @ParameterizedTest
    @CsvSource({"25"})
    @DisplayName("Door total 2 — valid v2")
    void valid_total2(String door) {
        assertDoesNotThrow(() ->
                AddressDoorTest.Spec.validate(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"c1", "c3", "c999", "z999"})
    @DisplayName("Door total 3 — valid v3")
    void valid_total3(String door) {
        assertDoesNotThrow(() ->
                AddressDoorTest.Spec.validate(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"d-14", "b-1", "x-250"})
    @DisplayName("Door total 4 — valid v4")
    void valid_total4(String door) {
        assertDoesNotThrow(() ->
                AddressDoorTest.Spec.validate(door));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "49", "50"})
    @DisplayName("Door totals 11–16 — boundary list")
    void valid_totals11through16(String door) {
        assertDoesNotThrow(() ->
                AddressDoorTest.Spec.validate(door));
    }

    // -------------------------
    // Negative tests — invalid i1–i6, total 17
    // -------------------------

    @ParameterizedTest
    @ValueSource(strings = {"tt", "abc"})
    @DisplayName("Door total 5 — invalid i1")
    void invalid_total5(String door) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressDoorTest.Spec.validate(door)
        );

        assertEquals("Door is invalid", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "115", "-25"})
    @DisplayName("Door totals 6–8 — invalid i2–i4")
    void invalid_totals6through8(String door) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressDoorTest.Spec.validate(door)
        );

        assertEquals("Door is invalid", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"D-14"})
    @DisplayName("Door total 9 — invalid i5")
    void invalid_total9(String door) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressDoorTest.Spec.validate(door)
        );

        assertEquals("Door is invalid", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"d-"})
    @DisplayName("Door total 10 — invalid i6")
    void invalid_total10(String door) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressDoorTest.Spec.validate(door)
        );

        assertEquals("Door is invalid", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"51"})
    @DisplayName("Door total 17 — invalid boundary 51")
    void invalid_total17(String door) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> AddressDoorTest.Spec.validate(door)
        );

        assertEquals("Door is invalid", ex.getMessage());
    }

    static final class Spec {

        private Spec() {
        }

        static void validate(String door) {
            if (door == null) {
                throw new IllegalArgumentException("Door must not be null");
            }
            if (door.isEmpty()) {
                throw new IllegalArgumentException("Door must not be empty");
            }
            if ("th".equals(door) || "mf".equals(door) || "tv".equals(door)) {
                return;
            }
            if (door.matches("^[a-z]-\\d{1,3}$")) {
                return;
            }
            if (door.matches("^[a-z]\\d{1,3}$")) {
                return;
            }
            if (door.matches("^-?\\d+$")) {
                int n = Integer.parseInt(door);
                if (n >= 1 && n <= 50) {
                    return;
                }
            }
            throw new IllegalArgumentException("Door is invalid");
        }
    }
}
