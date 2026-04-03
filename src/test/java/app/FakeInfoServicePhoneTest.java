package app;

import app.daos.PostalCodeDAO;
import app.dtos.PhoneDTO;
import app.service.FakeInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class FakeInfoServicePhoneTest {

    @Mock
    private PostalCodeDAO postalCodeDAO;

    private FakeInfoService fakeInfoService;

    private static final String[] VALID_PREFIXES = {
            "2", "30", "31", "40", "41", "42", "50", "51", "52", "53", "60", "61", "71", "81", "91", "92", "93",
            "342", "344", "345", "346", "347", "348", "349", "356", "357", "359", "362", "365", "366", "389", "398",
            "431", "441", "462", "466", "468", "472", "474", "476", "478", "485", "486", "488", "489", "493", "494",
            "495", "496", "498", "499", "542", "543", "545", "551", "552", "556", "571", "572", "573", "574", "577",
            "579", "584", "586", "587", "589", "597", "598", "627", "629", "641", "649", "658", "662", "663", "664",
            "665", "667", "692", "693", "694", "697", "771", "772", "782", "783", "785", "786", "788", "789", "826",
            "827", "829"
    };

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fakeInfoService = new FakeInfoService(postalCodeDAO);
    }

    @Test
    void generatePhoneNumber_shouldReturn8Digits() {
        PhoneDTO result = fakeInfoService.generatePhoneNumber();

        assertNotNull(result);
        assertNotNull(result.getPhoneNumber());
        assertEquals(8, result.getPhoneNumber().length());
    }

    @Test
    void generatePhoneNumber_shouldContainOnlyDigits() {
        PhoneDTO result = fakeInfoService.generatePhoneNumber();

        assertTrue(result.getPhoneNumber().matches("\\d{8}"));
    }

    @Test
    void generatePhoneNumber_shouldStartWithValidPrefix() {
        PhoneDTO result = fakeInfoService.generatePhoneNumber();
        String phone = result.getPhoneNumber();

        boolean hasValidPrefix = false;
        for (String prefix : VALID_PREFIXES) {
            if (phone.startsWith(prefix)) {
                hasValidPrefix = true;
                break;
            }
        }

        assertTrue(hasValidPrefix, "Phone number starts with invalid prefix: " + phone);
    }

    @Test
    void generatePhoneNumber_shouldAlwaysMatchRules_whenCalledManyTimes() {
        for (int i = 0; i < 1000; i++) {
            PhoneDTO result = fakeInfoService.generatePhoneNumber();
            String phone = result.getPhoneNumber();

            assertNotNull(phone);
            assertEquals(8, phone.length());
            assertTrue(phone.matches("\\d{8}"));

            boolean hasValidPrefix = false;
            for (String prefix : VALID_PREFIXES) {
                if (phone.startsWith(prefix)) {
                    hasValidPrefix = true;
                    break;
                }
            }

            assertTrue(hasValidPrefix, "Invalid prefix found for phone: " + phone);
        }
    }
}