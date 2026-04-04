package app;

import app.util.PhoneValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "21234567",
            "30123456",
            "34212345",
            "34612345"
    })
    @DisplayName("Valid phone numbers")
    void validPhones(String phone) {
        // Tester gyldige telefonnumre fra dine valid test cases.
        // Formålet er at sikre, at validatoren accepterer numre
        // med korrekt længde og gyldigt prefix.
        assertTrue(PhoneValidator.isValid(phone));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567",
            "123456789",
            "3012345"
    })
    @DisplayName("Invalid length")
    void invalidLength(String phone) {
        // Tester ugyldige længder.
        // Formålet er at sikre, at et telefonnummer skal være præcis 8 cifre,
        // hverken kortere eller længere.
        assertFalse(PhoneValidator.isValid(phone));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "10123456",
            "34312345"
    })
    @DisplayName("Invalid prefix")
    void invalidPrefix(String phone) {
        // Tester numre med prefix som ikke er tilladt.
        // Formålet er at sikre, at validatoren afviser numre,
        // selvom de har 8 cifre, hvis prefix ikke er på den gyldige liste.
        assertFalse(PhoneValidator.isValid(phone));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "30A23456"
    })
    @DisplayName("Non-numeric characters")
    void nonNumericCharacters(String phone) {
        // Tester telefonnumre der indeholder bogstaver eller andre ikke-cifre.
        // Formålet er at sikre, at kun numeriske værdier accepteres.
        assertFalse(PhoneValidator.isValid(phone));
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        // Tester tom streng.
        // Formålet er at sikre, at et tomt input ikke bliver accepteret som gyldigt telefonnummer.
        assertFalse(PhoneValidator.isValid(""));
    }

    @Test
    @DisplayName("Null input")
    void nullInput() {
        // Tester null-værdi.
        // Formålet er at sikre, at validatoren håndterer null sikkert
        // og returnerer false i stedet for at crashe.
        assertFalse(PhoneValidator.isValid(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "21234567",
            "30123456",
            "34212345",
            "34612345",
            "1234567",
            "123456789",
            "10123456",
            "3012345",
            "30A23456",
            "34312345"
    })
    @DisplayName("Return type is always boolean")
    void alwaysReturnsBoolean(String phone) {
        // Tester at metoden altid returnerer en boolean-værdi uden at kaste fejl.
        // Formålet er at bekræfte stabil opførsel for både gyldige og ugyldige inputs.
        assertDoesNotThrow(() -> PhoneValidator.isValid(phone));
    }
}