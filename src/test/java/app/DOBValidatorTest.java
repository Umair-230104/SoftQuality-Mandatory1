package app;

import app.util.DOBValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DOBValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "1900-01-01",
            "2000-05-10",
            "2025-12-31"
    })
    void validDates(String date) {
        LocalDate dob = LocalDate.parse(date);
        assertTrue(DOBValidator.isValid(dob));
    }

    @Test
    void nullDob() {
        assertFalse(DOBValidator.isValid(null));
    }

    @ParameterizedTest
    @CsvSource({
            "1900-01-01, true",
            "1899-12-31, false",
            "1901-01-01, true",
            "2025-01-01, true",
            "2026-01-01, true",
            "2027-01-01, false"
    })
    void yearBoundaryCases(String date, boolean expected) {
        LocalDate dob = LocalDate.parse(date);
        assertEquals(expected, DOBValidator.isValid(dob));
    }

    // JAVA TIME FORMAT DOESNT ALLOW FOR ILLEGAL FORMATS LIKE MONTH = 13 OR DATE = 33
    @ParameterizedTest
    @CsvSource({
            "2000-01-01, true",
            "2000-11-01, true",
            "2000-12-01, true"
    })
    void validMonths(String date, boolean expected) {
        LocalDate dob = LocalDate.parse(date);
        assertEquals(expected, DOBValidator.isValid(dob));
    }

    @ParameterizedTest
    @CsvSource({
            "2000-01-01, true",
            "2000-01-02, true",
            "2000-01-30, true",
            "2000-01-31, true"
    })
    void validDays(String date, boolean expected) {
        LocalDate dob = LocalDate.parse(date);
        assertEquals(expected, DOBValidator.isValid(dob));
    }
}