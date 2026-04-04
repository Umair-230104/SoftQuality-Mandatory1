package app.util;

import java.util.ArrayList;
import java.util.List;

public class AddressValidator {

    // ---------------- DOOR ----------------

    public static void validateDoor(String door) {
        if (door == null) {
            throw new IllegalArgumentException("Door must not be null");
        }
        if (door.isEmpty()) {
            throw new IllegalArgumentException("Door must not be empty");
        }

        if (door.equals("th") || door.equals("mf") || door.equals("tv")) {
            return;
        }

        if (door.matches("[a-z]-\\d{1,3}") || door.matches("[a-z]\\d{1,3}")) {
            return;
        }

        if (door.matches("-?\\d+")) {
            int n = Integer.parseInt(door);
            if (n >= 1 && n <= 50) {
                return;
            }
        }

        throw new IllegalArgumentException("Door is invalid");
    }

    // ---------------- FLOOR ----------------

    public static void validateFloor(String floor) {
        if (floor == null) {
            throw new IllegalArgumentException("Floor must not be null");
        }
        if (floor.isEmpty()) {
            throw new IllegalArgumentException("Floor must not be empty");
        }
        if (floor.length() > 2) {
            throw new IllegalArgumentException("Floor has invalid length");
        }

        if (floor.equals("st")) {
            return;
        }

        if (!floor.matches("[1-9]\\d?")) {
            throw new IllegalArgumentException("Floor must be \"st\" or 1–99");
        }

        int value = Integer.parseInt(floor);
        if (value < 1 || value > 99) {
            throw new IllegalArgumentException("Floor out of range");
        }
    }

    // ---------------- NUMBER ----------------

    // NOTE:
// Previously, only a subset of uppercase letters was allowed as suffix (e.g., excluding I, J, O, Q).
// This caused a mismatch with the address generator, which produces any letter from A-Z.
//
// According to the assignment requirements, a house number may be followed by
// an optional uppercase letter (no restriction on specific letters).
//
// Therefore, the validation has been updated to allow ALL uppercase letters (A-Z),
// while still rejecting lowercase letters and invalid formats.


    private static final String HOUSE_NUMBER_SUFFIX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void validateNumber(String number) {
        if (number == null) {
            throw new IllegalArgumentException("Number must not be null");
        }
        if (number.isEmpty()) {
            throw new IllegalArgumentException("Number must not be empty");
        }

        int i = 0;
        while (i < number.length() && Character.isDigit(number.charAt(i))) {
            i++;
        }

        if (i == 0) {
            throw new IllegalArgumentException("Number must start with a digit");
        }

        String digitPart = number.substring(0, i);

        if (digitPart.length() > 3) {
            throw new IllegalArgumentException("Number has too many digits");
        }

        int numeric = Integer.parseInt(digitPart);
        if (numeric < 1 || numeric > 999) {
            throw new IllegalArgumentException("Number out of range");
        }

        if (i == number.length()) return;

        if (number.length() - i != 1) {
            throw new IllegalArgumentException("Number must have at most one suffix letter");
        }

        char suffix = number.charAt(i);

        if (!Character.isLetter(suffix)) {
            throw new IllegalArgumentException("Number contains a special character");
        }

        if (!Character.isUpperCase(suffix)) {
            throw new IllegalArgumentException("Suffix letter must be uppercase");
        }

        if (HOUSE_NUMBER_SUFFIX.indexOf(suffix) < 0) {
            throw new IllegalArgumentException("Suffix letter is not allowed");
        }
    }

    // ---------------- STREET ----------------

    public static void validateStreet(String street) {
        if (street == null) {
            throw new IllegalArgumentException("Street must not be null");
        }
        if (street.isEmpty()) {
            throw new IllegalArgumentException("Street must not be empty");
        }
        if (street.length() > 40) {
            throw new IllegalArgumentException("Street must be at most 40 characters");
        }

        for (char c : street.toCharArray()) {
            if (c == ' ') continue;

            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException("Street contains an invalid character");
            }
        }
    }
}