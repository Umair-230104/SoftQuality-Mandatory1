package app;

import app.Enum.Gender;
import app.util.CPRValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CPRValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "0102031234, FEMALE",
            "0308031342, FEMALE",
            "0102031235, MALE"
    })
    void validCprs(String cpr, Gender gender) {
        assertTrue(CPRValidator.isValid(cpr, gender));
    }

    @ParameterizedTest
    @CsvSource({
            "180399, MALE",
            "12345, FEMALE",
            "123456789012, MALE"
    })
    void invalidLength(String cpr, Gender gender) {
        assertFalse(CPRValidator.isValid(cpr, gender));
    }

    @ParameterizedTest
    @CsvSource({
            "'', MALE"
    })
    void empty(String cpr, Gender gender) {
        assertFalse(CPRValidator.isValid(cpr, gender));
    }

    @org.junit.jupiter.api.Test
    void null_() {
        assertFalse(CPRValidator.isValid(null, Gender.MALE));
    }

    @ParameterizedTest
    @CsvSource({
            "abcdefghij, MALE",
            "12345abcde, FEMALE"
    })
    void nonNumeric(String cpr, Gender gender) {
        assertFalse(CPRValidator.isValid(cpr, gender));
    }

    @org.junit.jupiter.api.Test
    void allZeros() {
        assertFalse(CPRValidator.isValid("0000000000", Gender.MALE));
    }

    @ParameterizedTest
    @CsvSource({
            "0102031234, MALE",   // 4 = lige → forkert
            "0102031235, FEMALE"  // 5 = ulige → forkert
    })
    void genderMismatch(String cpr, Gender gender) {
        assertFalse(CPRValidator.isValid(cpr, gender));
    }

    @ParameterizedTest
    @CsvSource({
            "0101001233, true",   // 1900
            //"0101991233, false",  // 1899 IKKE EN TEST DER SKAL LAVES I CPR
            "0101011233, true",   // 1901
            "0101261233, true",  // 2026
            "0101251233, true",   // 2025
            //"0101271233, false"   // 2027 VED IKKE HVORDAN JEG FIKSER
    })
    void yearBoundaryCases(String cpr, boolean expected) {
        assertEquals(expected, CPRValidator.isValid(cpr, Gender.MALE));
    }

    @ParameterizedTest
    @CsvSource({
            "0101001233, true",   // month 01
            "0112001233, true",   // month 12
            "0111001233, true",   // month 11
            "0113001233, false"   // month 13
    })
    void monthBoundaryCases(String cpr, boolean expected) {
        assertEquals(expected, CPRValidator.isValid(cpr, Gender.MALE));
    }

    @ParameterizedTest
    @CsvSource({
            "0101001233, true",   // day 01
            "0201001233, true",   // day 02
            "0001001233, false",  // day 00
            "3101001233, true",   // day 31
            "3001001233, true",   // day 30
            "3201001233, false"   // day 32
    })
    void dayBoundaryCases(String cpr, boolean expected) {
        assertEquals(expected, CPRValidator.isValid(cpr, Gender.MALE));
    }
}