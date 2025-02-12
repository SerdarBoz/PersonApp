package com.personApp;

import java.util.List;
import java.util.stream.Collectors;

public class PersonService {
    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public boolean insert(Person person){
        return personRepository.insert(person);
    }

    public boolean update(Person person){
        return personRepository.update(person);
    }

    public boolean delete(long id){
        return personRepository.delete(id);
    }

    public List<String> getByFirstNameAndLastName(String firstName, String lastName){
        List<Person> users = personRepository.getByFirstNameAndLastName(firstName, lastName);
        return users.stream().map(x -> "Id: " + x.getId() +
                ", First name: " + x.getFirstName() +
                ", Last name: " + x.getLastName() +
                ", Birthdate: " + x.getBirthDate()).collect(Collectors.toList());
    }

    public List<String> getByFirstName(String firstName) {
        List<Person> users = personRepository.getByFirstName(firstName);
        return users.stream().map(x -> "Id: " + x.getId() +
                ", First name: " + x.getFirstName() +
                ", Last name: " + x.getLastName() +
                ", Birthdate: " + x.getBirthDate()).collect(Collectors.toList());
    }

    public List<String> getByLastName(String lastName){
        List<Person> users = personRepository.getByLastName(lastName);
        return users.stream().map(x -> "Id: " + x.getId() +
                ", First name: " + x.getFirstName() +
                ", Last name: " + x.getLastName() +
                ", Birthdate: " + x.getBirthDate()).collect(Collectors.toList());
    }

    public List<String> getAllUsers(){
        List<Person> users = personRepository.getAllUsers();
        return users.stream().map(x -> "Id: " + x.getId() +
                ", First name: " + x.getFirstName() +
                ", Last name: " + x.getLastName() +
                ", Birthdate: " + x.getBirthDate()).collect(Collectors.toList());

    }
}
