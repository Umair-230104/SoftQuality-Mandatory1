package app.util;

import java.time.LocalDate;

public class DOBValidator {

    public static boolean isValid(LocalDate dob) {
        if (dob == null) return false;

        LocalDate min = LocalDate.of(1900, 1, 1);
        LocalDate max = LocalDate.now();

        if (dob.isBefore(min)) return false;
        if (dob.isAfter(max)) return false;

        return true;
    }
}