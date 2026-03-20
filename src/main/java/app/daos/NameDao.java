package app.daos;

import app.Enum.Gender;
import app.dtos.NameGenderDTO;
import app.dtos.PersonNameJsonDTO;
import app.dtos.PersonNamesWrapperDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NameDao {

    private final List<PersonNameJsonDTO> persons;
    private final Random random = new Random();

    public NameDao() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("person-names.json");

            if (inputStream == null) {
                throw new RuntimeException("Could not find person-names.json in resources folder");
            }

            PersonNamesWrapperDTO wrapper =
                    objectMapper.readValue(inputStream, PersonNamesWrapperDTO.class);

            this.persons = wrapper.getPersons();

            if (persons == null || persons.isEmpty()) {
                throw new RuntimeException("No persons found in person-names.json");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load person names from JSON", e);
        }
    }

    public NameGenderDTO getRandomNameGender() {
        PersonNameJsonDTO person = persons.get(random.nextInt(persons.size()));

        return new NameGenderDTO(
                person.getFirstName(),
                person.getLastName(),
                mapGender(person.getGender())
        );
    }

    public NameGenderDTO getRandomNameGenderByGender(Gender gender) {
        List<PersonNameJsonDTO> filtered = persons.stream()
                .filter(p -> mapGender(p.getGender()) == gender)
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            throw new RuntimeException("No persons found for gender: " + gender);
        }

        PersonNameJsonDTO person = filtered.get(random.nextInt(filtered.size()));

        return new NameGenderDTO(
                person.getFirstName(),
                person.getLastName(),
                gender
        );
    }

    private Gender mapGender(String gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Gender value is null");
        }

        return switch (gender.trim().toLowerCase()) {
            case "male" -> Gender.MALE;
            case "female" -> Gender.FEMALE;
            default -> throw new IllegalArgumentException("Unknown gender value: " + gender);
        };
    }
}