package com.personApp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static PersonService personService = new PersonService(new PersonRepository());

    public static void main(String[] args) {
        boolean stop = false;

        do {
            System.out.println();
            System.out.println("Beheer personen:");
            System.out.println("================");
            System.out.println("a) Persoon toevoegen");
            System.out.println("b) Lijst van personen");
            System.out.println("c) Personen opzoeken a.d.h.v. voornaam en/of achternaam");
            System.out.println("d) Persoon wijzigen");
            System.out.println("e) Persoon verwijderen");
            System.out.println("z) Stop");
            System.out.print("Keuze: ");
            String keuze = new Scanner(System.in).nextLine();

            switch (keuze) {
                case "a":
                    System.out.println();
                    System.out.println("Persoon toevoegen:");
                    System.out.println("==================");
                    System.out.print("Voornaam: ");
                    String firstNameInsert = new Scanner(System.in).nextLine();
                    System.out.print("Achternaam: ");
                    String lastNameInsert = new Scanner(System.in).nextLine();
                    String birthdateInsert = "";
                    do {
                        System.out.print("Geboortedatum: (dd/mm/yyyy) ");
                        birthdateInsert = new Scanner(System.in).nextLine();
                    } while (!isDateValid(birthdateInsert));
                    LocalDate birthDateInsert2 = LocalDate.parse(birthdateInsert, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    boolean rowHasBeenInserted = personService.insert(new Person(firstNameInsert, lastNameInsert, birthDateInsert2));
                    if (rowHasBeenInserted) {
                        System.out.println("Toevoegen gelukt!");
                    } else {
                        System.out.println("Toevoegen NIET gelukt!");
                    }
                    break;
                case "b":
                    System.out.println();
                    System.out.println("Lijst van personen:");
                    System.out.println("===================");
                    List<String> allUsers = personService.getAllUsers();
                    allUsers.forEach(x -> System.out.println(x));
                    break;
                case "c":
                    System.out.println();
                    System.out.println("Personen opzoeken a.d.h.v. voornaam en/of achternaam:");
                    System.out.println("=====================================================");
                    List<String> allUsersBy;
                    String firstNameSearch = "";
                    String lastNameSearch = "";
                    do {
                        System.out.print("Voornaam: ");
                        firstNameSearch = new Scanner(System.in).nextLine();
                        System.out.print("Achternaam: ");
                        lastNameSearch = new Scanner(System.in).nextLine();
                    } while (firstNameSearch.isBlank() && lastNameSearch.isBlank());

                    if (!firstNameSearch.isBlank() && !lastNameSearch.isBlank()) {
                        allUsersBy = personService.getByFirstNameAndLastName(firstNameSearch, lastNameSearch);
                    } else if (!firstNameSearch.isBlank()) {
                        allUsersBy = personService.getByFirstName(firstNameSearch);
                    } else {
                        allUsersBy = personService.getByLastName(lastNameSearch);
                    }
                    allUsersBy.forEach(x -> System.out.println(x));
                    break;
                case "d":
                    System.out.println();
                    System.out.println("Persoon wijzigen:");
                    System.out.println("=================");
                    String idToBeUpdated = "";
                    do {
                        System.out.print("ID: ");
                        idToBeUpdated = new Scanner(System.in).nextLine();
                    } while (!isIdValid(idToBeUpdated));

                    System.out.print("Voornaam: ");
                    String firstNameUpdate = new Scanner(System.in).nextLine();
                    System.out.print("Achternaam: ");
                    String lastNameUpdate = new Scanner(System.in).nextLine();
                    String birthdateUpdate = "";
                    do {
                        System.out.print("Geboortedatum: (dd/mm/yyyy) ");
                        birthdateUpdate = new Scanner(System.in).nextLine();
                    } while (!isDateValid(birthdateUpdate));
                    LocalDate birthdateUpdate2 = LocalDate.parse(birthdateUpdate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    boolean hasRowBeenUpdated = personService.update(new Person(Long.valueOf(idToBeUpdated), firstNameUpdate, lastNameUpdate, birthdateUpdate2));
                    if (hasRowBeenUpdated) {
                        System.out.println("Wijzigen gelukt!");
                    } else {
                        System.out.println("Wijzigen NIET gelukt!");
                    }
                    break;
                case "e":
                    System.out.println();
                    System.out.println("Persoon verwijderen:");
                    System.out.println("====================");
                    String idToBeDeleted = "";
                    do {
                        System.out.print("ID: ");
                        idToBeDeleted = new Scanner(System.in).nextLine();
                    } while (!isIdValid(idToBeDeleted));
                    boolean hasRowBeenDeleted = personService.delete(Long.valueOf(idToBeDeleted));
                    if (hasRowBeenDeleted) {
                        System.out.println("Verwijderen gelukt!");
                    } else {
                        System.out.println("Verwijderen NIET gelukt!");
                    }
                    break;
                case "z":
                    stop = true;
                    break;
                default:
                    System.out.println();
                    System.out.println("Keuze bestaat niet!");
                    break;
            }
        } while (!stop);
    }

    public static boolean isIdValid(String id) {
        try {
            Long.valueOf(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isDateValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}