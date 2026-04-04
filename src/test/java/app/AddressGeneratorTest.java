package app;

import app.daos.PostalCodeDAO;
import app.dtos.AddressDTO;
import app.entities.PostalCode;
import app.service.FakeInfoService;
import app.util.AddressValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressGeneratorTest {

    private FakeInfoService fakeInfoService;
    private PostalCodeDAO postalCodeDAO;

    @BeforeEach
    void setup() {
        // Mock DAO to avoid DB access
        postalCodeDAO = mock(PostalCodeDAO.class);
        when(postalCodeDAO.getRandomPostalCode())
                .thenReturn(new PostalCode("1000", "Copenhagen"));

        fakeInfoService = new FakeInfoService(postalCodeDAO);
    }

    // ---------------------------------
    // SINGLE ADDRESS VALIDATION
    // ---------------------------------

    @Test
    @DisplayName("Generated address is fully valid")
    void testGeneratedAddressIsValid() {
        AddressDTO address = fakeInfoService.generateAddress();

        assertNotNull(address);

        assertDoesNotThrow(() ->
                AddressValidator.validateStreet(address.getStreet()));

        assertDoesNotThrow(() ->
                AddressValidator.validateNumber(address.getNumber()));

        assertDoesNotThrow(() ->
                AddressValidator.validateFloor(address.getFloor()));

        assertDoesNotThrow(() ->
                AddressValidator.validateDoor(address.getDoor()));

        assertEquals("1000", address.getPostalCode());
        assertEquals("Copenhagen", address.getTown());
    }

    // ---------------------------------
    // MULTIPLE GENERATED ADDRESSES
    // ---------------------------------

    @Test
    @DisplayName("Multiple generated addresses are valid")
    void testGenerateMultipleAddresses() {
        for (int i = 0; i < 10; i++) {
            AddressDTO address = fakeInfoService.generateAddress();

            assertNotNull(address);

            assertDoesNotThrow(() ->
                    AddressValidator.validateStreet(address.getStreet()));

            assertDoesNotThrow(() ->
                    AddressValidator.validateNumber(address.getNumber()));

            assertDoesNotThrow(() ->
                    AddressValidator.validateFloor(address.getFloor()));

            assertDoesNotThrow(() ->
                    AddressValidator.validateDoor(address.getDoor()));

            assertEquals("1000", address.getPostalCode());
            assertEquals("Copenhagen", address.getTown());
        }
    }

    // ---------------------------------
    // BASIC STRUCTURE CHECKS
    // ---------------------------------

    @Test
    @DisplayName("Generated address fields are never null or empty")
    void testFieldsNotNullOrEmpty() {
        AddressDTO address = fakeInfoService.generateAddress();

        assertNotNull(address.getStreet());
        assertNotNull(address.getNumber());
        assertNotNull(address.getFloor());
        assertNotNull(address.getDoor());

        assertFalse(address.getStreet().isEmpty());
        assertFalse(address.getNumber().isEmpty());
        assertFalse(address.getFloor().isEmpty());
        assertFalse(address.getDoor().isEmpty());
    }
}