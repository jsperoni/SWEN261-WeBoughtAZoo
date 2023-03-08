package com.zoo.api.zooapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Customer class
 *
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class CustomerTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_username = "Wi-Fire";
        String expected_password = "Password";
        String[] expected_personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] expected_card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] expected_history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        // Invoke
        Customer customer = new Customer(expected_id, expected_username, expected_password, expected_personal, expected_card, expected_history);

        // Analyze
        assertEquals(expected_id,customer.getId());
        assertEquals(expected_username,customer.getUsername());
        assertEquals(expected_username,customer.getPassword());
        assertEquals(expected_personal,customer.getPersonal());
        assertEquals(expected_card,customer.getCard());
    }

    @Test
    public void testUsername() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(id, username, password, personal, card, history);

        String expected_username = "Galactic Agent";

        // Invoke
        customer.setUsername(expected_username);

        // Analyze
        assertEquals(expected_username,customer.getUsername());
    }

    @Test
    public void testPassword() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(id, username, password, personal, card, history);

        String expected_password = "password";

        // Invoke
        customer.setPassword(expected_password);

        // Analyze
        assertEquals(expected_password,customer.getPassword());
    }
    @Test
    public void testPersonal() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(id, username, password, personal, card, history);

        String[] expected_personal = {"newAddress", "newZip", "newPhone"};

        // Invoke
        customer.setPersonal(expected_personal);

        // Analyze
        assertEquals(expected_personal,customer.getPersonal());
    }
    @Test
    public void testCard() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(id, username, password, personal, card, history);

        String[] expected_card = {"newName", "newNum", "newExp", "newCvv", "newZip"};

        // Invoke
        customer.setPersonal(expected_card);

        // Analyze
        assertEquals(expected_card,customer.getCard());
    }
    @Test
    public void testHistory() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        Customer customer = new Customer(id, username, password, personal, card, history);

        String[] expected_history = {"newAnimal1", "newAnimal2", "newAnimal3"};

        // Invoke
        customer.setPersonal(expected_history);

        // Analyze
        assertEquals(expected_history,customer.getHistory());
    }
    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        String[] history = {"Bongo", "Lightning", "Zeus", "Apollo", "Scooby"};
        String expected_string = String.format(Customer.STRING_FORMAT,id,username,password);
        Customer customer = new Customer(id, username, password, personal, card, history);

        // Invoke
        String actual_string = customer.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}