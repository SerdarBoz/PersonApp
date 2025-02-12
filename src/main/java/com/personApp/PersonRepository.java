package com.personApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final String DB_URL = "jdbc:mysql://localhost/*********";
    private final String USERNAME = "*********";
    private final String PASSWORD = "****************";

    public boolean insert(Person person){
        if(person.getFirstName() == null || person.getLastName() == null || person.getBirthDate() == null){
            throw new IllegalArgumentException();
        }
        String updatePositionSql = "INSERT INTO person(firstname, lastname, birthdate) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(updatePositionSql)){
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setDate(3, Date.valueOf(person.getBirthDate()));
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(Person person){
        if(person.getFirstName() == null || person.getLastName() == null || person.getBirthDate() == null){
            throw new IllegalArgumentException();
        }
        String updatePositionSql = "UPDATE person SET firstname = ?, lastname = ?, birthdate = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(updatePositionSql)){
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setDate(3, Date.valueOf(person.getBirthDate()));
            ps.setLong(4, person.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        if(id < 1) {
            throw new IllegalArgumentException();
        }
        String updatePositionSql = "DELETE FROm person WHERE id = ?";
        try(Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        PreparedStatement ps = connection.prepareStatement(updatePositionSql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Person> getByFirstNameAndLastName(String firstName, String lastName) throws IllegalArgumentException{
        if (firstName == null && lastName == null) {
            throw new IllegalArgumentException("Je moet voornaam of achternaam ingeven!");
        }
        String query = "SELECT * FROM person WHERE firstname = ? AND lastname = ?";
        List<Person> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setFirstName(resultSet.getString("firstname"));
                person.setLastName(resultSet.getString("lastname"));
                person.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
                users.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Person> getByFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException();
        }
        String query = "SELECT * FROM person WHERE firstname = ?";
        List<Person> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, firstName);

            ResultSet myResultSet = ps.executeQuery();
            while (myResultSet.next()) {
                Person person = new Person();
                person.setId(myResultSet.getInt("id"));
                person.setFirstName(myResultSet.getString("firstname"));
                person.setLastName(myResultSet.getString("lastname"));
                person.setBirthDate(myResultSet.getDate("birthdate").toLocalDate());
                users.add(person);
            }
            myResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Person> getByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException();
        }
        String query = "SELECT * FROM person WHERE lastname = ?";
        List<Person> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, lastName);
            ResultSet myResultSet = ps.executeQuery();
            while (myResultSet.next()) {
                Person person = new Person();
                person.setId(myResultSet.getInt("id"));
                person.setFirstName(myResultSet.getString("firstname"));
                person.setLastName(myResultSet.getString("lastname"));
                person.setBirthDate(myResultSet.getDate("birthdate").toLocalDate());
                users.add(person);
            }
            myResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Person> getAllUsers() {
        List<Person> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM person")) {
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setFirstName(resultSet.getString("firstname"));
                person.setLastName(resultSet.getString("lastname"));
                person.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
                users.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}