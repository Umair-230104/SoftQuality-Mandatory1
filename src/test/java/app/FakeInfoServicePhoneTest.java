package app;

import app.daos.PostalCodeDAO;
import app.dtos.PhoneDTO;
import app.service.FakeInfoService;
import app.util.PhoneValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class FakeInfoServicePhoneTest {

    @Mock
    private PostalCodeDAO postalCodeDAO;

    private FakeInfoService fakeInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fakeInfoService = new FakeInfoService(postalCodeDAO);
    }

    @Test
    @DisplayName("Generated phone number should not be null")
    void generatePhoneNumber_shouldNotBeNull() {
        // Tester at service-metoden faktisk returnerer et objekt og et telefonnummer.
        // Formålet er at sikre, at genereringen ikke giver null tilbage.
        PhoneDTO result = fakeInfoService.generatePhoneNumber();

        assertNotNull(result);
        assertNotNull(result.getPhoneNumber());
    }

    @Test
    @DisplayName("Generated phone number should be valid")
    void generatePhoneNumber_shouldBeValid() {
        // Tester at et genereret telefonnummer overholder reglerne i PhoneValidator.
        // Formålet er at sikre, at generatoren kun laver gyldige telefonnumre.
        PhoneDTO result = fakeInfoService.generatePhoneNumber();

        assertTrue(PhoneValidator.isValid(result.getPhoneNumber()));
    }

    @Test
    @DisplayName("Generated phone number should always be 8 digits")
    void generatePhoneNumber_shouldAlwaysBe8Digits() {
        // Tester at det genererede telefonnummer altid består af præcis 8 cifre.
        // Formålet er at sikre korrekt længde på output fra generatoren.
        PhoneDTO result = fakeInfoService.generatePhoneNumber();

        assertEquals(8, result.getPhoneNumber().length());
    }

    @Test
    @DisplayName("Generated phone number should contain only digits")
    void generatePhoneNumber_shouldContainOnlyDigits() {
        // Tester at generatoren ikke laver bogstaver eller specialtegn i telefonnummeret.
        // Formålet er at sikre, at output kun består af numeriske tegn.
        PhoneDTO result = fakeInfoService.generatePhoneNumber();

        assertTrue(result.getPhoneNumber().matches("\\d{8}"));
    }

    @Test
    @DisplayName("Generated phone numbers should always be valid when called many times")
    void generatePhoneNumber_shouldAlwaysMatchRules_whenCalledManyTimes() {
        // Tester generatoren mange gange i træk.
        // Formålet er at sikre, at generatoren er stabil
        // og ikke kun laver korrekt output én enkelt gang.
        for (int i = 0; i < 1000; i++) {
            PhoneDTO result = fakeInfoService.generatePhoneNumber();

            assertNotNull(result);
            assertNotNull(result.getPhoneNumber());
            assertTrue(PhoneValidator.isValid(result.getPhoneNumber()));
        }
    }
}