package app.util;

import app.Enum.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CPRValidator {

    public static boolean isValid(String cpr, Gender gender) {
        if (cpr == null) return false;

        // længde
        if (cpr.length() != 10) return false;

        // kun tal
        if (!cpr.matches("\\d{10}")) return false;

        String datePart = cpr.substring(0, 6);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
            LocalDate.parse(datePart, formatter);
        } catch (Exception e) {
            return false;
        }

        int lastDigit = Character.getNumericValue(cpr.charAt(9));

        if (gender == Gender.MALE && lastDigit % 2 == 0) {
            return false;
        }

        if (gender == Gender.FEMALE && lastDigit % 2 != 0) {
            return false;
        }

        return true;
    }
}