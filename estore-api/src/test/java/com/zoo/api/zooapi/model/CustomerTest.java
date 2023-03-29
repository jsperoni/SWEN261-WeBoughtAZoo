package com.zoo.api.zooapi.model;

import static org.junit.jupiter.api.Assertions.*;

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
        String[] expected_personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] expected_card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        int[] expected_history = {1, 2, 3, 4, 5};          // Invoke
        Customer customer = new Customer(expected_id, expected_username, expected_personal, expected_card, expected_history);

        // Analyze
        assertEquals(expected_id,customer.getId());
        assertEquals(expected_username,customer.getUsername());
        assertEquals(expected_personal,customer.getPersonal());
        assertEquals(expected_card,customer.getCard());
    }

    @Test
    public void testUsername() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        int[] expected_history = {1, 2, 3, 4, 5};
        Customer customer = new Customer(id, username, personal, card, expected_history);

        String expected_username = "Galactic Agent";

        // Invoke
        customer.setUsername(expected_username);

        // Analyze
        assertEquals(expected_username,customer.getUsername());
    }

    @Test
    public void testPersonal() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        int[] history = {1, 2, 3, 4, 5};
        Customer customer = new Customer(id, username, personal, card, history);

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
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        int[] history = {1, 2, 3, 4, 5};
        Customer customer = new Customer(id, username, personal, card, history);

        String[] expected_card = {"newName", "newNum", "newExp", "newCvv", "newZip"};

        // Invoke
        customer.setCard(expected_card);

        // Analyze
            assertArrayEquals(expected_card,customer.getCard());
    }
    @Test
    public void testHistory() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        int[] history = {1, 2, 3, 4, 5};
        Customer customer = new Customer(id, username, personal, card, history);

        int[] expected_history = {1, 2, 3};

        // Invoke
        customer.setHistory(3);

        // Analyze
        assertArrayEquals(expected_history,customer.getHistory());
    }
    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String[] personal = {"6000 Reynolds Drive", "14623", "7743647789"};
        String[] card = {"John Doe", "1111111111111111", "01/23", "111", "14623"};
        int[] history = {1, 2, 3, 4, 5};
//        String expected_string = String.format(Customer.STRING_FORMAT, id, username,personal,card,history);
        String expected_string = String.format(Customer.STRING_FORMAT, id, username);

        Customer customer = new Customer(id, username, personal, card, history);

        // Invoke
        String actual_string = customer.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}