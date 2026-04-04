package app.service;

import app.daos.NameDao;
import app.daos.PostalCodeDAO;
import app.dtos.*;
import app.Enum.Gender;
import app.entities.PostalCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeInfoService {

    private static final int MIN_BULK_PERSONS = 2;
    private static final int MAX_BULK_PERSONS = 100;
    private final NameDao nameDao = new NameDao();
    private final PostalCodeDAO postalCodeDAO;

    public FakeInfoService(PostalCodeDAO postalCodeDAO) {
        this.postalCodeDAO = postalCodeDAO;
    }

    private static final String[] PHONE_PREFIXES = {
            "2", "30", "31", "40", "41", "42", "50", "51", "52", "53", "60", "61", "71", "81", "91", "92", "93",
            "342", "344", "345", "346", "347", "348", "349", "356", "357", "359", "362", "365", "366", "389", "398",
            "431", "441", "462", "466", "468", "472", "474", "476", "478", "485", "486", "488", "489", "493", "494",
            "495", "496", "498", "499", "542", "543", "545", "551", "552", "556", "571", "572", "573", "574", "577",
            "579", "584", "586", "587", "589", "597", "598", "627", "629", "641", "649", "658", "662", "663", "664",
            "665", "667", "692", "693", "694", "697", "771", "772", "782", "783", "785", "786", "788", "789", "826",
            "827", "829"
    };

    private static final DateTimeFormatter CPR_DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");

    private final Random random = new Random();

    public CprDTO generateCpr() {
        NameGenderDobDTO person = generateNameGenderDob();
        return new CprDTO(generateCprFrom(person.getDateOfBirth(), person.getGender()));
    }

    public NameGenderDTO generateNameGender() {
        return nameDao.getRandomNameGender();
    }

    public NameGenderDobDTO generateNameGenderDob() {
        NameGenderDTO nameGender = generateNameGender();
        LocalDate dob = generateBirthDate();

        return new NameGenderDobDTO(
                nameGender.getFirstName(),
                nameGender.getLastName(),
                nameGender.getGender(),
                dob
        );
    }

    public CprNameGenderDTO generateCprNameGender() {
        NameGenderDobDTO person = generateNameGenderDob();
        String cpr = generateCprFrom(person.getDateOfBirth(), person.getGender());

        return new CprNameGenderDTO(
                cpr,
                person.getFirstName(),
                person.getLastName(),
                person.getGender()
        );
    }

    public CprNameGenderDobDTO generateCprNameGenderDob() {
        NameGenderDobDTO person = generateNameGenderDob();
        String cpr = generateCprFrom(person.getDateOfBirth(), person.getGender());

        return new CprNameGenderDobDTO(
                cpr,
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getDateOfBirth()
        );
    }

    public AddressDTO generateAddress() {
        String street = generateRandomText(12 + random.nextInt(20)).trim();
        String number = String.valueOf(random.nextInt(999) + 1);

        if (random.nextInt(10) < 2) {
            number += (char) ('A' + random.nextInt(26));
        }

        String floor = random.nextInt(10) < 3
                ? "st"
                : String.valueOf(random.nextInt(99) + 1);

        String door = generateDoor();

        PostalCode postalCode = postalCodeDAO.getRandomPostalCode();

        return new AddressDTO(
                street,
                number,
                floor,
                door,
                postalCode.getPostalCode(),
                postalCode.getTownName()
        );
    }

    public PhoneDTO generatePhoneNumber() {
        String prefix = PHONE_PREFIXES[random.nextInt(PHONE_PREFIXES.length)];
        StringBuilder phone = new StringBuilder(prefix);

        while (phone.length() < 8) {
            phone.append(random.nextInt(10));
        }

        return new PhoneDTO(phone.toString());
    }

    public PersonDTO generatePerson() {
        NameGenderDobDTO base = generateNameGenderDob();
        String cpr = generateCprFrom(base.getDateOfBirth(), base.getGender());
        AddressDTO address = generateAddress();
        PhoneDTO phone = generatePhoneNumber();

        return new PersonDTO(
                cpr,
                base.getFirstName(),
                base.getLastName(),
                base.getGender(),
                base.getDateOfBirth(),
                address,
                phone.getPhoneNumber()
        );
    }

    public List<PersonDTO> generatePeople(int count) {
        if (count < MIN_BULK_PERSONS || count > MAX_BULK_PERSONS) {
            throw new IllegalArgumentException("Count must be between 2 and 100");
        }

        List<PersonDTO> people = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            people.add(generatePerson());
        }
        return people;
    }

    private LocalDate generateBirthDate() {
        LocalDate start = LocalDate.of(1900, 1, 1);
        LocalDate end = LocalDate.now();

        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();

        long randomDay = startEpochDay +
                (long) (random.nextDouble() * (endEpochDay - startEpochDay));

        LocalDate dob = LocalDate.ofEpochDay(randomDay);

        if (!app.util.DOBValidator.isValid(dob)) {
            throw new IllegalStateException("Generated DOB is invalid: " + dob);
        }

        return dob;
    }

    private String generateCprFrom(LocalDate birthDate, Gender gender) {
        String firstSix = birthDate.format(CPR_DATE_FORMAT);

        int digit7 = random.nextInt(10);
        int digit8 = random.nextInt(10);
        int digit9 = random.nextInt(10);
        int lastDigit = random.nextInt(10);

        if (gender == Gender.FEMALE && lastDigit % 2 != 0) {
            lastDigit = (lastDigit == 9) ? 8 : lastDigit + 1;
        } else if (gender == Gender.MALE && lastDigit % 2 == 0) {
            lastDigit = (lastDigit == 8) ? 9 : lastDigit + 1;
        }

        String cpr = firstSix + digit7 + digit8 + digit9 + lastDigit;

        if (!app.util.CPRValidator.isValid(cpr, gender)) {
            throw new IllegalStateException("Generated CPR is invalid: " + cpr);
        }
        return cpr;
    }

    private String generateDoor() {
        int doorType = 1 + random.nextInt(20);

        if (doorType < 8) return "th";
        if (doorType < 15) return "tv";
        if (doorType < 17) return "mf";
        if (doorType < 19) return String.valueOf(1 + random.nextInt(50));

        char letter = (char) ('a' + random.nextInt(26));
        String suffix = String.valueOf(1 + random.nextInt(999));

        if (doorType == 20) {
            return letter + "-" + suffix;
        }
        return letter + suffix;
    }

    private String generateRandomText(int length) {
        String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZæøåÆØÅ ";
        StringBuilder result = new StringBuilder();

        result.append(validChars.charAt(1 + random.nextInt(validChars.length() - 1)));

        for (int i = 1; i < length; i++) {
            result.append(validChars.charAt(random.nextInt(validChars.length())));
        }

        return result.toString();
    }
}