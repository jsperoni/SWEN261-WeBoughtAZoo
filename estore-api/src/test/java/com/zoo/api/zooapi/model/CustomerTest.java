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
        // Invoke
        Customer customer = new Customer(expected_id, expected_username, expected_password);

        // Analyze
        assertEquals(expected_id,customer.getId());
        assertEquals(expected_username,customer.getUsername());
        assertEquals(expected_username,customer.getPassword());
    }

    @Test
    public void testUsername() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";
        Customer customer = new Customer(id, username, password);

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
        Customer customer = new Customer(id, username, password);

        String expected_password = "password";

        // Invoke
        customer.setPassword(expected_password);

        // Analyze
        assertEquals(expected_password,customer.getPassword());
    }
    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String username = "Wi-Fire";
        String password = "password";

        String expected_string = String.format(Customer.STRING_FORMAT,id,username,password);
        Customer customer = new Customer(id, username, password);

        // Invoke
        String actual_string = customer.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}