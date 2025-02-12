import com.personApp.Person;
import com.personApp.PersonRepository;
import com.personApp.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonRepositoryMockTest {
    PersonRepository personRepository = Mockito.mock(PersonRepository.class);
    PersonService personService;

    @BeforeEach
    public void beforeAll(){
        personService = new PersonService(personRepository);
    }

    @Test
    public void testGetByFirstNameAndLastNameSuccess() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "Jan", "Bezos", LocalDate.of(1999, 1, 5)));
        Mockito.when(personRepository.getByFirstNameAndLastName("Jan", "Bezos")).thenReturn(persons);

        List<String> returnedList = personService.getByFirstNameAndLastName("Jan", "Bezos");

        assertEquals(1, returnedList.size());
        assertEquals("Id: 1, First name: Jan, Last name: Bezos, Birthdate: 1999-01-05", returnedList.get(0));

        Mockito.verify(personRepository).getByFirstNameAndLastName("Jan", "Bezos");
    }

    @Test
    public void testGetByFirstNameAndLastNameFail() {
        Mockito.when(personRepository.getByFirstNameAndLastName(null, null)).thenThrow(IllegalArgumentException.class);

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> personService.getByFirstNameAndLastName(null, null));

        assertNull(illegalArgumentException.getMessage());

        Mockito.verify(personRepository).getByFirstNameAndLastName(null, null);
    }
}
