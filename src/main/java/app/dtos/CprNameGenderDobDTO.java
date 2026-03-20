package app.dtos;

import app.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CprNameGenderDobDTO {
    private String cpr;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
}