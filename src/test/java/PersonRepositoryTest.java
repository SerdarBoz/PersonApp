import com.personApp.Person;
import com.personApp.PersonRepository;
import com.personApp.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class PersonRepositoryTest {
    PersonRepository personRepository = new PersonRepository();
    PersonService personService = new PersonService(personRepository);

    @BeforeEach
    public void init(){
        personRepository.getAllUsers().stream().forEach(x-> personRepository.delete(x.getId()));
    }

    @Test
    public void testInsertPersonSuccess() {
        LocalDate birthdate = LocalDate.of(1989, 4, 19);
        List<String> personsBefore = personService.getByFirstNameAndLastName("Jan", "Pieters");

        boolean insertResult = personService.insert(new Person("Jan", "Pieters", birthdate));

        assertTrue(insertResult);

        List<String> personsAfter = personService.getByFirstNameAndLastName("Jan", "Pieters");

        assertEquals(personsBefore.size() + 1, personsAfter.size());
        assertTrue(personsAfter.stream().anyMatch(x ->
                x.endsWith(", First name: Jan, Last name: Pieters, Birthdate: 1989-04-19")
        ));
    }

    @Test
    public void testInsertPersonFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            personService.insert(new Person(null, null, null));
        });
    }

    @Test
    public void testGetAllUsersSuccess() {
        LocalDate birthdateTim = LocalDate.of(1980, 4, 19);
        LocalDate birthdateTom = LocalDate.of(1980, 4, 19);

        personService.insert(new Person("Tim", "Pieters", birthdateTim));
        personService.insert(new Person("Tom", "Peeters", birthdateTom));

        List<String> persons = personService.getAllUsers();

        assertTrue(persons.size() >= 2);
        assertTrue(persons.stream().anyMatch(x ->
                x.endsWith(", First name: Tim, Last name: Pieters, Birthdate: 1980-04-19")
        ));
        assertTrue(persons.stream().anyMatch(x ->
                x.endsWith(", First name: Tom, Last name: Peeters, Birthdate: 1980-04-19")
        ));
    }

    @Test
    public void testUpdatePersonSuccess() {
        LocalDate birthdate = LocalDate.of(1989, 4, 19);
        Person person = new Person("Jan", "Pieters", birthdate);
        boolean insertResult = personService.insert(person);

        assertTrue(insertResult);

        List<Person> personsBefore = personRepository.getByFirstNameAndLastName("Jan", "Pieters");
        assertTrue(personsBefore.contains(person));

        Person insertedPerson = personsBefore.get(personsBefore.indexOf(person));
        insertedPerson.setFirstName("Tom");
        insertedPerson.setLastName("Peeters");
        boolean updateResult = personService.update(insertedPerson);

        assertTrue(updateResult);

        List<Person> personsAfter = personRepository.getByFirstNameAndLastName("Tom", "Peeters");
        assertTrue(personsAfter.stream().anyMatch(x -> x.getId() == insertedPerson.getId()));
        //Assertions.assertTrue(personsAfter.contains(insertedPerson));
        Person updatedPerson = personsAfter.get(personsAfter.indexOf(insertedPerson));

        assertEquals(insertedPerson.getId(), updatedPerson.getId());
        assertEquals(insertedPerson.getFirstName(), updatedPerson.getFirstName());
        assertEquals(insertedPerson.getLastName(), updatedPerson.getLastName());
        assertEquals(insertedPerson.getBirthDate(), updatedPerson.getBirthDate());

    }

    @Test
    public void testUpdatePersonFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            personService.update(new Person(99999,null, null, null));
        });
    }

    @Test
    public void testDeletePersonSuccess() {
        LocalDate birthdate = LocalDate.of(1989, 4, 19);
        Person person = new Person("Jan", "Pieters", birthdate);
        boolean insertResult = personService.insert(person);

        assertTrue(insertResult);

        List<Person> personsBefore = personRepository.getByFirstNameAndLastName("Jan", "Pieters");

        assertTrue(personsBefore.contains(person));
        Person insertedPerson = personsBefore.get(personsBefore.indexOf(person));
        boolean updateResult = personService.delete(insertedPerson.getId());

        assertTrue(updateResult);

        List<Person> personsAfter = personRepository.getByFirstNameAndLastName("Jan", "Pieters");
        assertFalse(personsAfter.stream().anyMatch(x -> x.getId() == insertedPerson.getId()));
    }
}