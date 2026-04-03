package app;


import app.daos.PostalCodeDAO;
import app.dtos.AddressDTO;
import app.entities.PostalCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import app.service.FakeInfoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressGeneratorTest {

    private FakeInfoService fakeInfoService;
    private PostalCodeDAO postalCodeDAO;

    @BeforeEach
    void setup() {
        // Mock PostalCodeDAO to avoid real DB access
        postalCodeDAO = mock(PostalCodeDAO.class);
        when(postalCodeDAO.getRandomPostalCode()).thenReturn(new PostalCode("1000", "Copenhagen"));

        fakeInfoService = new FakeInfoService(postalCodeDAO);
    }

    // ---------------------------------
    // STREET TESTS
    // ---------------------------------

    @Test
    @DisplayName("Street: valid random string length >= 12")
    void testStreetValidLength() {
        AddressDTO address = fakeInfoService.generateAddress();
        assertNotNull(address.getStreet());
        assertTrue(address.getStreet().length() >= 12, "Street should have at least 12 characters");
    }

    @Test
    @DisplayName("Street: cannot be null or empty")
    void testStreetNotNullOrEmpty() {
        AddressDTO address = fakeInfoService.generateAddress();
        assertNotNull(address.getStreet());
        assertFalse(address.getStreet().isEmpty(), "Street should not be empty");
    }

    // ---------------------------------
    // NUMBER TESTS
    // ---------------------------------

    @Test
    @DisplayName("Number: 1-999, optionally with uppercase letter")
    void testNumberRangeAndFormat() {
        AddressDTO address = fakeInfoService.generateAddress();
        String number = address.getNumber();
        assertNotNull(number);
        assertFalse(number.isEmpty());

        // Check numeric part
        String digits = number.replaceAll("[A-Z]", "");
        int num = Integer.parseInt(digits);
        assertTrue(num >= 1 && num <= 999, "Number should be 1-999");

        // Optional uppercase letter
        if (number.length() > digits.length()) {
            char letter = number.charAt(number.length() - 1);
            assertTrue(letter >= 'A' && letter <= 'Z', "Optional letter must be uppercase A-Z");
        }
    }

    // ---------------------------------
    // FLOOR TESTS
    // ---------------------------------

    @Test
    @DisplayName("Floor: valid values are 'st' or 1-99")
    void testFloorValid() {
        AddressDTO address = fakeInfoService.generateAddress();
        String floor = address.getFloor();
        assertNotNull(floor);

        if ("st".equals(floor)) return; // valid
        try {
            int f = Integer.parseInt(floor);
            assertTrue(f >= 1 && f <= 99, "Floor number must be 1-99");
        } catch (NumberFormatException e) {
            fail("Floor must be 'st' or a number 1-99");
        }
    }

    // ---------------------------------
    // DOOR TESTS
    // ---------------------------------

    @Test
    @DisplayName("Door: valid formats")
    void testDoorValid() {
        AddressDTO address = fakeInfoService.generateAddress();
        String door = address.getDoor();
        assertNotNull(door);

        boolean valid = door.matches("th|mf|tv") ||
                door.matches("^[a-z]\\d{1,3}$") ||
                door.matches("^[a-z]-\\d{1,3}$") ||
                (door.matches("\\d+") && Integer.parseInt(door) >= 1 && Integer.parseInt(door) <= 50);

        assertTrue(valid, "Door format is invalid: " + door);
    }

    // ---------------------------------
    // GENERATE MULTIPLE ADDRESSES
    // ---------------------------------

    @Test
    @DisplayName("Generate multiple addresses")
    void testGenerateMultipleAddresses() {
        for (int i = 0; i < 10; i++) {
            AddressDTO address = fakeInfoService.generateAddress();
            assertNotNull(address.getStreet());
            assertNotNull(address.getNumber());
            assertNotNull(address.getFloor());
            assertNotNull(address.getDoor());
            assertEquals("1000", address.getPostalCode());
            assertEquals("Copenhagen", address.getTown());
        }
    }
}